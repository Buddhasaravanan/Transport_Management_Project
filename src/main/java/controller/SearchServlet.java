package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dao.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a");
    private static final List<String> SUPPORTED_CITIES = Arrays.asList(
            "Chennai", "Madurai", "Coimbatore", "Bangalore", "Trichy", "Salem",
            "Erode", "Vellore", "Tirunelveli", "Pondicherry", "Tanjore", "Karur");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String date = request.getParameter("date");

        if (from == null || to == null || date == null
                || from.isBlank() || to.isBlank() || date.isBlank()) {
            request.setAttribute("citySuggestions", SUPPORTED_CITIES);
            request.getRequestDispatcher("/User/home.jsp").forward(request, response);
            return;
        }

        String normalizedFrom = normalizeLocation(from);
        String normalizedTo = normalizeLocation(to);
        String selectedBusType = safeTrim(request.getParameter("busType"));
        String departureSlot = safeTrim(request.getParameter("departureSlot"));
        Double minPrice = parseDouble(request.getParameter("minPrice"));
        Double maxPrice = parseDouble(request.getParameter("maxPrice"));

        ScheduleDAO dao = new ScheduleDAO();
        List<Object[]> results = dao.searchSchedules(normalizedFrom, normalizedTo, date.trim());

        List<Map<String, Object>> enrichedResults = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> card = new LinkedHashMap<>();

            int scheduleId = ((Number) row[0]).intValue();
            String busNumber = stringValue(row[1]);
            String source = stringValue(row[2]);
            String destination = stringValue(row[3]);
            String journeyDate = row[4] != null ? row[4].toString() : date.trim();
            String departureRaw = row[5] != null ? row[5].toString() : "";
            String arrivalRaw = row[6] != null ? row[6].toString() : "";
            double fare = row[7] instanceof Number ? ((Number) row[7]).doubleValue() : 0.0;

            String busType = inferBusType(busNumber, scheduleId);
            String operator = inferOperator(scheduleId, busType);
            String routeKey = source + "-" + destination;
            int availableSeats = 8 + Math.abs(routeKey.hashCode() + scheduleId) % 25;
            String duration = formatDuration(departureRaw, arrivalRaw);
            String departureTime = formatTime(departureRaw);
            String arrivalTime = formatTime(arrivalRaw);
            String rating = String.format(Locale.ENGLISH, "%.1f", 4.1 + (Math.abs(scheduleId) % 8) * 0.1);
            String amenities = amenitiesFor(busType);
            String boardingPoint = source + " Central Bus Stand";
            String droppingPoint = destination + " Main Stop";
            String mapUrl = buildMapUrl(source, destination);
            String departureBand = resolveDepartureSlot(departureRaw);

            card.put("scheduleId", scheduleId);
            card.put("busNumber", busNumber);
            card.put("source", source);
            card.put("destination", destination);
            card.put("journeyDate", journeyDate);
            card.put("departureTime", departureTime);
            card.put("arrivalTime", arrivalTime);
            card.put("fare", fare);
            card.put("busType", busType);
            card.put("operator", operator);
            card.put("availableSeats", availableSeats);
            card.put("duration", duration);
            card.put("rating", rating);
            card.put("amenities", amenities);
            card.put("boardingPoint", boardingPoint);
            card.put("droppingPoint", droppingPoint);
            card.put("mapUrl", mapUrl);
            card.put("departureSlot", departureBand);

            if (matchesFilters(card, selectedBusType, minPrice, maxPrice, departureSlot)) {
                enrichedResults.add(card);
            }
        }

        request.setAttribute("results", enrichedResults);
        request.setAttribute("searchFrom", normalizedFrom);
        request.setAttribute("searchTo", normalizedTo);
        request.setAttribute("searchDate", date.trim());
        request.setAttribute("routeMapUrl", buildMapUrl(normalizedFrom, normalizedTo));
        request.setAttribute("selectedBusType", selectedBusType);
        request.setAttribute("selectedDepartureSlot", departureSlot);
        request.setAttribute("selectedMinPrice", request.getParameter("minPrice") == null ? "" : request.getParameter("minPrice").trim());
        request.setAttribute("selectedMaxPrice", request.getParameter("maxPrice") == null ? "" : request.getParameter("maxPrice").trim());
        request.setAttribute("availableBusTypes", Arrays.asList("AC Sleeper", "Volvo Multi-Axle", "Non-AC Seater"));
        request.setAttribute("citySuggestions", SUPPORTED_CITIES);
        request.getRequestDispatcher("/User/Bus-List.jsp")
               .forward(request, response);
    }

    private static boolean matchesFilters(Map<String, Object> card, String busType, Double minPrice,
            Double maxPrice, String departureSlot) {
        String currentBusType = stringValue(card.get("busType"));
        double fare = card.get("fare") instanceof Number ? ((Number) card.get("fare")).doubleValue() : 0.0;
        String currentSlot = stringValue(card.get("departureSlot"));

        if (!busType.isEmpty() && !busType.equalsIgnoreCase(currentBusType)) {
            return false;
        }
        if (minPrice != null && fare < minPrice) {
            return false;
        }
        if (maxPrice != null && fare > maxPrice) {
            return false;
        }
        if (!departureSlot.isEmpty() && !departureSlot.equalsIgnoreCase(currentSlot)) {
            return false;
        }
        return true;
    }

    private static String normalizeLocation(String value) {
        String trimmed = value == null ? "" : value.trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }
        String lower = trimmed.toLowerCase(Locale.ENGLISH);
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    private static String stringValue(Object value) {
        return value == null ? "" : value.toString().trim();
    }

    private static String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private static Double parseDouble(String value) {
        String trimmed = safeTrim(value);
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Double.valueOf(trimmed);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static String inferBusType(String busNumber, int scheduleId) {
        String key = (busNumber + scheduleId).toLowerCase(Locale.ENGLISH);
        if (key.contains("sl") || scheduleId % 3 == 0) {
            return "AC Sleeper";
        }
        if (key.contains("vl") || scheduleId % 3 == 1) {
            return "Volvo Multi-Axle";
        }
        return "Non-AC Seater";
    }

    private static String inferOperator(int scheduleId, String busType) {
        if ("Volvo Multi-Axle".equals(busType)) {
            return scheduleId % 2 == 0 ? "GreenBus Premium" : "Skyline Volvo";
        }
        if ("AC Sleeper".equals(busType)) {
            return scheduleId % 2 == 0 ? "GreenLine Sleeper" : "Night Rider Express";
        }
        return scheduleId % 2 == 0 ? "CityHop Travels" : "BudgetRide Express";
    }

    private static String amenitiesFor(String busType) {
        if ("AC Sleeper".equals(busType)) {
            return "Charging Point • Sleeper Berth • Water Bottle";
        }
        if ("Volvo Multi-Axle".equals(busType)) {
            return "Live Tracking • AC • Charging Point";
        }
        return "Pushback Seats • Reading Light • Blankets";
    }

    private static String resolveDepartureSlot(String rawTime) {
        try {
            LocalTime departure = LocalTime.parse(rawTime);
            if (departure.isBefore(LocalTime.NOON)) {
                return "Morning";
            }
            if (departure.isBefore(LocalTime.of(17, 0))) {
                return "Afternoon";
            }
            if (departure.isBefore(LocalTime.of(21, 0))) {
                return "Evening";
            }
            return "Night";
        } catch (DateTimeParseException e) {
            return "";
        }
    }

    private static String formatTime(String rawTime) {
        try {
            return LocalTime.parse(rawTime).format(TIME_FORMAT);
        } catch (DateTimeParseException e) {
            return rawTime;
        }
    }

    private static String formatDuration(String departureRaw, String arrivalRaw) {
        try {
            LocalTime departure = LocalTime.parse(departureRaw);
            LocalTime arrival = LocalTime.parse(arrivalRaw);
            Duration duration = Duration.between(departure, arrival);
            if (duration.isNegative() || duration.isZero()) {
                duration = duration.plusHours(24);
            }
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            return hours + "h " + minutes + "m";
        } catch (DateTimeParseException e) {
            return "--";
        }
    }

    private static String buildMapUrl(String from, String to) {
        String route = from + " to " + to;
        return "https://www.google.com/maps?q="
                + URLEncoder.encode(route, StandardCharsets.UTF_8)
                + "&output=embed";
    }
}