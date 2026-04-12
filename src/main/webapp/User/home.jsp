<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%
String today = java.time.LocalDate.now().toString();
String contextPath = request.getContextPath();
List<String> citySuggestions = (List<String>) request.getAttribute("citySuggestions");
if (citySuggestions == null || citySuggestions.isEmpty()) {
    citySuggestions = Arrays.asList("Chennai", "Madurai", "Coimbatore", "Bangalore", "Trichy", "Salem",
            "Erode", "Vellore", "Tirunelveli", "Pondicherry", "Tanjore", "Karur");
}
%>
<!DOCTYPE html>
<html>
<head>
<title>GreenBus | Book Bus Tickets</title>

<link rel="stylesheet" href="<%=contextPath%>/css/Reset.css">
<link rel="stylesheet" href="<%=contextPath%>/css/Header.css">
<link rel="stylesheet" href="<%=contextPath%>/css/Home.css">

</head>
<body>

<jsp:include page="header.jsp" />

<main class="home-page">
<div class="hero">
    <div class="hero-content">
        <div class="hero-text">
            <span class="hero-badge">India's trusted green travel platform</span>
            <h1>Book real bus routes with live-ready schedules, boarding points, and instant PDF tickets</h1>
            <p>Search multiple routes, compare bus types, view route maps, and complete your trip in a few clicks.</p>
        </div>

        <form class="search-bar"
              action="<%=contextPath%>/SearchServlet"
              method="get">

            <div class="input-box">
                <label>From</label>
                <input type="text" name="from" placeholder="Chennai" list="citySuggestionsFrom" required>
                <small>Top routes across Tamil Nadu and nearby cities</small>
            </div>

            <div class="swap" aria-hidden="true">⇄</div>

            <div class="input-box">
                <label>To</label>
                <input type="text" name="to" placeholder="Madurai" list="citySuggestionsTo" required>
                <small>Choose destination from supported city routes</small>
            </div>

            <div class="input-box">
                <label>Date</label>
                <input type="date" name="date" min="<%=today%>" value="<%=today%>" required>
                <small>Book current and upcoming schedules quickly</small>
            </div>

            <button class="search-btn" type="submit">
                Search Buses
            </button>

        </form>

        <datalist id="citySuggestionsFrom">
<%
for (String city : citySuggestions) {
%>
            <option value="<%=city%>"></option>
<%
}
%>
        </datalist>
        <datalist id="citySuggestionsTo">
<%
for (String city : citySuggestions) {
%>
            <option value="<%=city%>"></option>
<%
}
%>
        </datalist>

        <div class="hero-stats">
            <div class="stat-card">
                <strong>120+</strong>
                <span>Daily schedules</span>
            </div>
            <div class="stat-card">
                <strong>35+</strong>
                <span>Top city routes</span>
            </div>
            <div class="stat-card">
                <strong>24x7</strong>
                <span>Booking support</span>
            </div>
        </div>
    </div>
</div>

<section class="section-wrap route-section">
    <div class="section-head">
        <div>
            <p class="section-kicker">Popular routes</p>
            <h2>Top searched city connections</h2>
        </div>
        <span class="section-pill">Multiple routes · More departures</span>
    </div>

    <div class="route-grid">
        <a class="route-card" href="<%=contextPath%>/SearchServlet?from=Chennai&to=Madurai&date=<%=today%>">
            <h3>Chennai → Madurai</h3>
            <p>20+ services · AC Sleeper · Night departures</p>
            <span>View schedules</span>
        </a>
        <a class="route-card" href="<%=contextPath%>/SearchServlet?from=Coimbatore&to=Bangalore&date=<%=today%>">
            <h3>Coimbatore → Bangalore</h3>
            <p>15+ services · Volvo · Business travel</p>
            <span>View schedules</span>
        </a>
        <a class="route-card" href="<%=contextPath%>/SearchServlet?from=Trichy&to=Chennai&date=<%=today%>">
            <h3>Trichy → Chennai</h3>
            <p>12+ services · Day & night buses</p>
            <span>View schedules</span>
        </a>
        <a class="route-card" href="<%=contextPath%>/SearchServlet?from=Salem&to=Coimbatore&date=<%=today%>">
            <h3>Salem → Coimbatore</h3>
            <p>8+ services · Budget friendly options</p>
            <span>View schedules</span>
        </a>
    </div>
</section>

<section class="section-wrap schedule-section">
    <div class="section-head">
        <div>
            <p class="section-kicker">Why GreenBus feels real</p>
            <h2>Everything a real booking project should show</h2>
        </div>
    </div>

    <div class="feature-grid">
        <div class="feature-card">
            <h3>Multiple routes</h3>
            <p>Support city-to-city search with route-specific buses, fares, and departure options.</p>
        </div>
        <div class="feature-card">
            <h3>Bus schedules</h3>
            <p>Display departure and arrival times clearly so users can compare and choose the best service.</p>
        </div>
        <div class="feature-card">
            <h3>Boarding points</h3>
            <p>Show pickup and drop locations near major bus stands for a more practical travel flow.</p>
        </div>
        <div class="feature-card">
            <h3>Route maps</h3>
            <p>Embed route maps directly on results so the experience feels closer to production apps.</p>
        </div>
    </div>
</section>

<section class="section-wrap map-section">
    <div class="section-head">
        <div>
            <p class="section-kicker">Route preview</p>
            <h2>Popular route maps</h2>
        </div>
        <span class="section-pill">Multiple maps · Clear route view</span>
    </div>

    <div class="map-grid">
        <article class="map-card">
            <div class="map-layout">
                <div class="map-info-card">
                    <h3>Chennai to Madurai</h3>
                    <ul>
                        <li>Frequent night and early morning departures</li>
                        <li>Balanced AC and sleeper bus availability</li>
                        <li>Major boarding points across Chennai</li>
                        <li>Smooth booking with downloadable PDF ticket</li>
                    </ul>
                </div>

                <div class="map-frame-card">
                    <iframe
                        title="Chennai to Madurai route map"
                        src="https://www.google.com/maps?q=Chennai%20to%20Madurai&output=embed"
                        loading="lazy"
                        referrerpolicy="no-referrer-when-downgrade"
                        allowfullscreen>
                    </iframe>
                </div>
            </div>
        </article>

        <article class="map-card">
            <div class="map-layout">
                <div class="map-info-card">
                    <h3>Coimbatore to Bangalore</h3>
                    <ul>
                        <li>Best suited for business and weekend travel</li>
                        <li>Good daytime and overnight service mix</li>
                        <li>Common pickup points near city hubs</li>
                        <li>Quick compare and seat selection workflow</li>
                    </ul>
                </div>

                <div class="map-frame-card">
                    <iframe
                        title="Coimbatore to Bangalore route map"
                        src="https://www.google.com/maps?q=Coimbatore%20to%20Bangalore&output=embed"
                        loading="lazy"
                        referrerpolicy="no-referrer-when-downgrade"
                        allowfullscreen>
                    </iframe>
                </div>
            </div>
        </article>
    </div>
</section>

<section class="section-wrap support-section">
    <div class="support-card">
        <div>
            <p class="section-kicker">Need help?</p>
            <h2>Support for search, seats, tickets, and boarding details</h2>
            <p class="support-text">Get help with finding buses, choosing seats, downloading your ticket, or checking boarding and dropping information for your trip.</p>
        </div>
        <div class="support-actions">
            <a class="support-btn primary" href="<%=contextPath%>/User/Contact.jsp">Contact Support</a>
            <a class="support-btn secondary" href="<%=contextPath%>/MyTicketsServlet">My Tickets</a>
        </div>
    </div>
</section>
</main>

<jsp:include page="Footer.jsp" />

</body>
</html>