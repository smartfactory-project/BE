package com.example.demo.statistics.service;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.statistics.model.StatisticsReport;
import com.example.demo.statistics.repository.StatisticsReportRepository;

@Service
public class StatisticsReportService {
    // simulation_name별 failed 평균 반환
    public double getAverageFailedBySimulationName(String simulationName) {
        List<StatisticsReport> reports = repository.findBySimulationName(simulationName);
        return reports.stream().mapToDouble(r -> r.getFailed()).average().orElse(0.0);
    }

    private final StatisticsReportRepository repository;

    public StatisticsReportService(StatisticsReportRepository repository) {
        this.repository = repository;
    }

    // 기존 방식 유지
    public void parseAndSaveReport(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String simulationName = originalFilename != null ? originalFilename.replaceFirst("\\.[^.]+$", "") : "unknown";
        parseAndSaveReport(file, simulationName);
    }

    // 파일명 기반 simulation_name 저장, upsert 방식
    public void parseAndSaveReport(MultipartFile file, String simulationName) throws Exception {
        String html = new String(file.getBytes(), StandardCharsets.UTF_8);
        Document doc = Jsoup.parse(html);
        double simulationSeconds = parseSimulationSeconds(doc.select("tr:contains(Simulation time) td").text());
        String simulationFile = doc.select("tr:contains(Model name) td").text();
        String simulationTimeStr = doc.select("tr:contains(Created on) td").text();
        LocalDateTime simulationTime = parseSimulationTime(simulationTimeStr);

        Elements rows = doc.select("table.Auswertungen tbody tr");
        for (Element row : rows) {
            String objectName = row.select("th.RIdx, th").text().trim();
            Elements tds = row.select("td");
            if (tds.size() < 10) continue;
            if (objectName.isEmpty()) continue;
            if (!isNumeric(tds.get(0).text().replace("%", "").trim())) continue;

            StatisticsReport entity = new StatisticsReport();
            entity.setSimulationName(simulationName);
            entity.setSimulationFile(simulationFile);
            entity.setSimulationTime(simulationTime);
            entity.setObjectName(objectName);
            entity.setWorking(parsePercentSafe(tds.get(0).text()));
            entity.setSetup(parsePercentSafe(tds.get(1).text()));
            entity.setWaiting(parsePercentSafe(tds.get(2).text()));
            entity.setBlocking(parsePercentSafe(tds.get(3).text()));
            entity.setPowering(parsePercentSafe(tds.get(4).text()));
            entity.setFailed(parsePercentSafe(tds.get(5).text()));
            entity.setStopped(parsePercentSafe(tds.get(6).text()));
            entity.setPaused(parsePercentSafe(tds.get(7).text()));
            entity.setUnplanned(parsePercentSafe(tds.get(8).text()));
            entity.setPortion(parsePercentSafe(tds.get(9).text()));

            Elements auswertTables = doc.select("table.Auswertungen");
            for (Element table : auswertTables) {
                Elements ths = table.select("thead th");
                if (ths.size() < 2) continue;
                boolean hasEntries = false, hasExits = false;
                int idxEntries = -1, idxExits = -1, idxMin = -1, idxMax = -1, idxRelEmpty = -1, idxRelFull = -1, idxOccNoInt = -1, idxOccWithInt = -1;
                for (int i = 0; i < ths.size(); i++) {
                    String thText = ths.get(i).text().toLowerCase();
                    if (thText.contains("entries")) { hasEntries = true; idxEntries = i-1; }
                    if (thText.contains("exits")) { hasExits = true; idxExits = i-1; }
                    if (thText.contains("min")) idxMin = i-1;
                    if (thText.contains("max")) idxMax = i-1;
                    if (thText.contains("relative empty")) idxRelEmpty = i-1;
                    if (thText.contains("relative full")) idxRelFull = i-1;
                    if (thText.contains("occupation") && thText.contains("no")) idxOccNoInt = i-1;
                    if (thText.contains("occupation") && thText.contains("interrupt")) idxOccWithInt = i-1;
                }
                if (!(hasEntries && hasExits)) continue;
                Elements trs = table.select("tbody tr");
                for (Element tr : trs) {
                    String lObj = tr.select("th.RIdx, th").text().trim();
                    if (normalizeName(lObj).equals(normalizeName(objectName))) {
                        Elements ltds = tr.select("td");
                        if (idxEntries >= 0 && idxEntries < ltds.size()) entity.setEntries(parseIntSafe(ltds.get(idxEntries).text()));
                        if (idxExits >= 0 && idxExits < ltds.size()) entity.setExits(parseIntSafe(ltds.get(idxExits).text()));
                        if (idxMin >= 0 && idxMin < ltds.size()) entity.setMinContents(parseIntSafe(ltds.get(idxMin).text()));
                        if (idxMax >= 0 && idxMax < ltds.size()) entity.setMaxContents(parseIntSafe(ltds.get(idxMax).text()));
                        if (idxRelEmpty >= 0 && idxRelEmpty < ltds.size()) entity.setRelativeEmpty(parsePercentSafe(ltds.get(idxRelEmpty).text()));
                        if (idxRelFull >= 0 && idxRelFull < ltds.size()) entity.setRelativeFull(parsePercentSafe(ltds.get(idxRelFull).text()));
                        if (idxOccNoInt >= 0 && idxOccNoInt < ltds.size()) entity.setOccupationNoInterrupt(parsePercentSafe(ltds.get(idxOccNoInt).text()));
                        if (idxOccWithInt >= 0 && idxOccWithInt < ltds.size()) entity.setOccupationWithInterrupt(parsePercentSafe(ltds.get(idxOccWithInt).text()));
                        break;
                    }
                }
                break;
            }

            entity.setWorkingTimeSum(simulationSeconds * entity.getWorking() / 100.0);
            entity.setWorkingTimeMean(null);
            entity.setWorkingTimeStd(null);
            entity.setWaitingTimeSum(simulationSeconds * entity.getWaiting() / 100.0);
            entity.setWaitingTimeMean(null);
            entity.setWaitingTimeStd(null);
            entity.setBlockedTimeSum(simulationSeconds * entity.getBlocking() / 100.0);
            entity.setBlockedTimeMean(null);
            entity.setBlockedTimeStd(null);
            entity.setFailedTimeSum(simulationSeconds * entity.getFailed() / 100.0);
            entity.setFailedTimeMean(null);
            entity.setFailedTimeStd(null);

            // simulation_name, object_name 기준으로 upsert
            List<StatisticsReport> existing = repository.findBySimulationNameAndObjectName(
                simulationName, objectName);
            if (!existing.isEmpty()) {
                entity.setId(existing.get(0).getId());
            }
            repository.save(entity);
        }
    }

    // simulation_name별 평균 exits 반환
    public double getAverageExitsBySimulationName(String simulationName) {
        List<StatisticsReport> reports = repository.findBySimulationName(simulationName);
        return reports.stream()
            .filter(r -> r.getExits() != null)
            .mapToInt(StatisticsReport::getExits)
            .average().orElse(0.0);
    }
    // simulation_name별 평균 quality 반환
    public double getAverageQualityBySimulationName(String simulationName) {
        List<StatisticsReport> reports = repository.findBySimulationName(simulationName);
        return reports.stream().mapToDouble(r -> r.getRelativeFull() != null ? r.getRelativeFull() : 0.0).average().orElse(0.0);
    }
    // simulation_name별 평균 working 반환
    public double getAverageWorkingBySimulationName(String simulationName) {
        List<StatisticsReport> reports = repository.findBySimulationName(simulationName);
        return reports.stream()
            .filter(r -> r.getWorking() != 0.0)
            .mapToDouble(StatisticsReport::getWorking)
            .average().orElse(0.0);
    }

    // 시뮬레이션 시간(초) 파싱: "1:13:13:12.7094" 또는 "2:01:24.8451" 등 지원
    private double parseSimulationSeconds(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) return 0.0;
        String[] parts = timeStr.split(":");
        double seconds = 0.0;
        try {
            if (parts.length == 4) {
                // days:hours:minutes:seconds
                int days = Integer.parseInt(parts[0]);
                int hours = Integer.parseInt(parts[1]);
                int minutes = Integer.parseInt(parts[2]);
                double secs = Double.parseDouble(parts[3]);
                seconds = days * 86400 + hours * 3600 + minutes * 60 + secs;
            } else if (parts.length == 3) {
                // hours:minutes:seconds
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                double secs = Double.parseDouble(parts[2]);
                seconds = hours * 3600 + minutes * 60 + secs;
            }
        } catch (Exception e) {
            return 0.0;
        }
        return seconds;
    }

    // 설비명 정규화(소문자, 영숫자만)
    private String normalizeName(String name) {
        return name == null ? "" : name.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    // 안전하게 int 파싱
    private Integer parseIntSafe(String text) {
        try {
            return Integer.parseInt(text.replaceAll("[^0-9]", "").trim());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    // 안전하게 double 파싱
    private Double parseDoubleSafe(String text) {
        try {
            return Double.parseDouble(text.replaceAll("[^0-9.\\-]", "").trim());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    // 숫자인지 판별하는 유틸
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 안전하게 퍼센트 파싱, 실패 시 0 반환
    private double parsePercentSafe(String text) {
        try {
            return Double.parseDouble(text.replace("%", "").trim());
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0;
        }
    }

    private LocalDateTime parseSimulationTime(String text) {
        try {
            text = text.replace("오후", "PM").replace("오전", "AM");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 E a h:mm", java.util.Locale.KOREAN);
            return LocalDateTime.parse(text, formatter);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }
        // 최신 시뮬레이션 기준 설비별 통계 반환 (overview/lines API용)
        public List<StatisticsReport> getLatestReports() {
            List<StatisticsReport> all = repository.findAll();
            if (all.isEmpty()) return Collections.emptyList();
            LocalDateTime latestSimTime = all.stream()
                .map(StatisticsReport::getSimulationTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);
            if (latestSimTime == null) return Collections.emptyList();
            List<StatisticsReport> latest = new ArrayList<>();
            for (StatisticsReport r : all) {
                if (latestSimTime.equals(r.getSimulationTime())) {
                    latest.add(r);
                }
            }
            return latest;
        }
}