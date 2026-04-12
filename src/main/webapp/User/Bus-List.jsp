<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
List<Map<String, Object>> list = (List<Map<String, Object>>) request.getAttribute("results");
String searchFrom = (String) request.getAttribute("searchFrom");
String searchTo = (String) request.getAttribute("searchTo");
String searchDate = (String) request.getAttribute("searchDate");
String routeMapUrl = (String) request.getAttribute("routeMapUrl");
String selectedBusType = request.getAttribute("selectedBusType") == null ? "" : request.getAttribute("selectedBusType").toString();
String selectedDepartureSlot = request.getAttribute("selectedDepartureSlot") == null ? "" : request.getAttribute("selectedDepartureSlot").toString();
String selectedMinPrice = request.getAttribute("selectedMinPrice") == null ? "" : request.getAttribute("selectedMinPrice").toString();
String selectedMaxPrice = request.getAttribute("selectedMaxPrice") == null ? "" : request.getAttribute("selectedMaxPrice").toString();
List<String> availableBusTypes = (List<String>) request.getAttribute("availableBusTypes");
if (availableBusTypes == null || availableBusTypes.isEmpty()) {
    availableBusTypes = Arrays.asList("AC Sleeper", "Volvo Multi-Axle", "Non-AC Seater");
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Available Buses | GreenBus</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Bus-list.css">

</head>
<body>

<jsp:include page="header.jsp" />

<div class="results-shell">

    <section class="results-banner">
        <div>
            <p class="results-label">Live route search</p>
            <h2><%= searchFrom %> to <%= searchTo %></h2>
            <p class="results-subtitle">Travel date: <strong><%= searchDate %></strong> · Verified schedules, popular routes, and live-ready boarding details.</p>
        </div>
        <a class="modify-search-btn" href="<%=request.getContextPath()%>/User/home.jsp">Modify Search</a>
    </section>

    <section class="route-search-card">
        <form class="route-search-form" action="<%=request.getContextPath()%>/SearchServlet" method="get">
            <div class="compact-field">
                <label>From</label>
                <input type="text" name="from" value="<%= searchFrom %>" required>
            </div>
            <div class="compact-field">
                <label>To</label>
                <input type="text" name="to" value="<%= searchTo %>" required>
            </div>
            <div class="compact-field">
                <label>Date</label>
                <input type="date" name="date" value="<%= searchDate %>" required>
            </div>
            <button class="search-btn-inline" type="submit">Update Search</button>
        </form>
    </section>

    <div class="results-grid">
        <aside class="filter-sidebar">
            <form class="filter-card" action="<%=request.getContextPath()%>/SearchServlet" method="get">
                <input type="hidden" name="from" value="<%= searchFrom %>">
                <input type="hidden" name="to" value="<%= searchTo %>">
                <input type="hidden" name="date" value="<%= searchDate %>">

                <div class="filter-head">
                    <div>
                        <p class="filter-kicker">Refine results</p>
                        <h3>Filters</h3>
                    </div>
                    <a class="clear-filter-link" href="<%=request.getContextPath()%>/SearchServlet?from=<%= searchFrom %>&to=<%= searchTo %>&date=<%= searchDate %>">Clear</a>
                </div>

                <div class="filter-group">
                    <label for="busType">Bus Type</label>
                    <select id="busType" name="busType">
                        <option value="">All bus types</option>
<%
for (String type : availableBusTypes) {
    boolean isSelected = type.equalsIgnoreCase(selectedBusType);
%>
                        <option value="<%= type %>" <%= isSelected ? "selected" : "" %>><%= type %></option>
<%
}
%>
                    </select>
                </div>

                <div class="filter-group two-col">
                    <div>
                        <label for="minPrice">Min Price</label>
                        <input id="minPrice" type="number" name="minPrice" min="0" step="1" placeholder="300" value="<%= selectedMinPrice %>">
                    </div>
                    <div>
                        <label for="maxPrice">Max Price</label>
                        <input id="maxPrice" type="number" name="maxPrice" min="0" step="1" placeholder="1500" value="<%= selectedMaxPrice %>">
                    </div>
                </div>

                <div class="filter-group">
                    <label for="departureSlot">Departure Slot</label>
                    <select id="departureSlot" name="departureSlot">
                        <option value="">Any time</option>
                        <option value="Morning" <%= "Morning".equalsIgnoreCase(selectedDepartureSlot) ? "selected" : "" %>>Morning</option>
                        <option value="Afternoon" <%= "Afternoon".equalsIgnoreCase(selectedDepartureSlot) ? "selected" : "" %>>Afternoon</option>
                        <option value="Evening" <%= "Evening".equalsIgnoreCase(selectedDepartureSlot) ? "selected" : "" %>>Evening</option>
                        <option value="Night" <%= "Night".equalsIgnoreCase(selectedDepartureSlot) ? "selected" : "" %>>Night</option>
                    </select>
                </div>

                <button class="apply-filter-btn" type="submit">Apply Filters</button>
            </form>

            <div class="side-card map-card mobile-hide">
                <div class="side-card-head">
                    <h3>Route map</h3>
                    <span><%= searchFrom %> → <%= searchTo %></span>
                </div>
                <iframe
                    src="<%= routeMapUrl %>"
                    loading="lazy"
                    referrerpolicy="no-referrer-when-downgrade"
                    allowfullscreen>
                </iframe>
            </div>
        </aside>

        <div class="results-main">
            <div class="results-toolbar">
                <div>
                    <span class="toolbar-count"><%= list == null ? 0 : list.size() %></span>
                    <span class="toolbar-text">buses found</span>
                </div>
                <div class="toolbar-pill">Multiple routes · Premium and budget buses</div>
            </div>

<%
if (list == null || list.isEmpty()) {
%>
            <div class="no-bus">
                <h3>No buses available for these filters</h3>
                <p>Try clearing filters, changing the price range, or searching another time slot on this route.</p>
            </div>
<%
} else {
    for (Map<String, Object> row : list) {
%>
            <article class="bus-card">
                <div class="bus-card-top">
                    <div>
                        <div class="operator-line">
                            <h3><%= row.get("operator") %></h3>
                            <span class="rating-badge">★ <%= row.get("rating") %></span>
                        </div>
                        <p class="bus-meta"><%= row.get("busType") %> · Bus No: <%= row.get("busNumber") %> · <%= row.get("departureSlot") %> service</p>
                    </div>
                    <div class="fare-panel">
                        <span class="fare-label">Starting from</span>
                        <div class="fare">₹ <%= row.get("fare") %></div>
                    </div>
                </div>

                <div class="timeline-row">
                    <div class="time-block">
                        <strong><%= row.get("departureTime") %></strong>
                        <span><%= row.get("source") %></span>
                    </div>
                    <div class="journey-track">
                        <span class="duration"><%= row.get("duration") %></span>
                        <div class="track-line"></div>
                        <small>Direct route</small>
                    </div>
                    <div class="time-block align-right">
                        <strong><%= row.get("arrivalTime") %></strong>
                        <span><%= row.get("destination") %></span>
                    </div>
                </div>

                <div class="route-meta-grid">
                    <div>
                        <label>Boarding</label>
                        <p><%= row.get("boardingPoint") %></p>
                    </div>
                    <div>
                        <label>Dropping</label>
                        <p><%= row.get("droppingPoint") %></p>
                    </div>
                    <div>
                        <label>Seats left</label>
                        <p class="seats-left"><%= row.get("availableSeats") %> seats</p>
                    </div>
                    <div>
                        <label>Amenities</label>
                        <p><%= row.get("amenities") %></p>
                    </div>
                </div>

                <div class="bus-card-bottom">
                    <div class="service-chip-wrap">
                        <span class="service-chip">On-time departure</span>
                        <span class="service-chip">Safe boarding support</span>
                        <span class="service-chip">Digital ticket</span>
                    </div>
                    <a class="select-seat-btn"
                       href="<%=request.getContextPath()%>/SeatServlet?scheduleId=<%=row.get("scheduleId")%>">
                        Select Seat
                    </a>
                </div>
            </article>
<%
    }
}
%>

            <div class="side-card map-card desktop-hide">
                <div class="side-card-head">
                    <h3>Route map</h3>
                    <span><%= searchFrom %> → <%= searchTo %></span>
                </div>
                <iframe
                    src="<%= routeMapUrl %>"
                    loading="lazy"
                    referrerpolicy="no-referrer-when-downgrade"
                    allowfullscreen>
                </iframe>
            </div>
        </div>

        <aside class="results-side">
            <div class="side-card map-card desktop-only">
                <div class="side-card-head">
                    <h3>Route map</h3>
                    <span><%= searchFrom %> → <%= searchTo %></span>
                </div>
                <iframe
                    src="<%= routeMapUrl %>"
                    loading="lazy"
                    referrerpolicy="no-referrer-when-downgrade"
                    allowfullscreen>
                </iframe>
            </div>

            <div class="side-card tips-card">
                <h3>Trip highlights</h3>
                <ul>
                    <li>Multiple schedule choices across the day</li>
                    <li>Easy seat selection and instant PDF ticket</li>
                    <li>Popular boarding points near major bus stands</li>
                    <li>Flexible options for premium and budget travel</li>
                </ul>
            </div>
        </aside>
    </div>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>