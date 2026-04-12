<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%
    Integer scheduleId = (Integer) session.getAttribute("scheduleId");
    String boardingName = (String) session.getAttribute("boardingName");
    String droppingName = (String) session.getAttribute("droppingName");
    Double seatPrice = (Double) session.getAttribute("seatPrice");

    String[] seatNos = (String[]) session.getAttribute("seatNos");
    String[] names = (String[]) session.getAttribute("names");
    String[] ages = (String[]) session.getAttribute("ages");
    String[] genders = (String[]) session.getAttribute("genders");

    int seatCount = (seatNos == null) ? 0 : seatNos.length;

    boolean missingRequired =
        (scheduleId == null || seatPrice == null || seatCount == 0 ||
         names == null || ages == null || genders == null ||
         names.length != seatCount || ages.length != seatCount || genders.length != seatCount);

    if (missingRequired) {
%>
        <h2 style="color:red; text-align:center">
            Booking data missing or invalid. Please restart booking.
        </h2>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Confirm Booking</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Passenger.css">
    <style>
        .confirm-shell {
            width: min(1100px, 92%);
            margin: 26px auto 40px;
        }

        .confirm-card {
            background: #fff;
            padding: 26px;
            border-radius: 24px;
            box-shadow: 0 14px 34px rgba(16, 55, 31, 0.08);
            border: 1px solid #e2efe6;
        }

        .confirm-head {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 16px;
            margin-bottom: 22px;
        }

        .confirm-head h2,
        .confirm-section h3 {
            color: #173c27;
        }

        .confirm-badge {
            background: #eaf8ef;
            color: #0f7a43;
            border: 1px solid #d7eedf;
            border-radius: 999px;
            padding: 9px 14px;
            font-weight: 700;
        }

        .confirm-grid {
            display: grid;
            grid-template-columns: minmax(0, 1.4fr) minmax(280px, 0.8fr);
            gap: 22px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 14px;
            overflow: hidden;
            border-radius: 16px;
        }

        th, td {
            padding: 14px 12px;
            border-bottom: 1px solid #edf4ef;
            text-align: center;
        }

        th {
            background: #0f7a43;
            color: white;
        }

        tr:nth-child(even) td {
            background: #fbfefd;
        }

        .confirm-section {
            background: #fbfefd;
            border: 1px solid #e4efe7;
            border-radius: 20px;
            padding: 20px;
        }

        .summary-row-confirm {
            display: flex;
            justify-content: space-between;
            gap: 14px;
            margin: 14px 0;
            color: #62756b;
        }

        .summary-row-confirm strong {
            color: #173c27;
        }

        .btn {
            margin-top: 24px;
            padding: 15px;
            width: 100%;
            background: linear-gradient(135deg, #0f7a43, #1aa15d);
            color: white;
            border: none;
            font-size: 17px;
            font-weight: 700;
            border-radius: 14px;
            cursor: pointer;
            box-shadow: 0 12px 24px rgba(15, 122, 67, 0.18);
        }

        @media (max-width: 860px) {
            .confirm-head,
            .summary-row-confirm {
                flex-direction: column;
                align-items: flex-start;
            }

            .confirm-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>
<jsp:include page="header.jsp" />

<div class="rb-steps">
    <span>1. Select seats</span>
    <span>2. Board/Drop point</span>
    <span>3. Passenger Info</span>
    <span class="active">4. Confirm booking</span>
</div>

<div class="confirm-shell">
    <div class="confirm-card">
        <div class="confirm-head">
            <div>
                <p class="section-kicker">Final review</p>
                <h2>Confirm your bus booking</h2>
            </div>
            <span class="confirm-badge"><%= seatCount %> passenger(s)</span>
        </div>

        <div class="confirm-grid">
            <div class="confirm-section">
                <h3>Passenger list</h3>
                <table>
                    <tr>
                        <th>Seat</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Gender</th>
                    </tr>

                    <%
                        for (int i = 0; i < seatCount; i++) {
                    %>
                    <tr>
                        <td><%= seatNos[i] %></td>
                        <td><%= names[i] %></td>
                        <td><%= ages[i] %></td>
                        <td><%= genders[i] %></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>

            <div class="confirm-section">
                <h3>Trip summary</h3>
                <div class="summary-row-confirm">
                    <span>Boarding</span>
                    <strong><%= (boardingName == null ? "-" : boardingName) %></strong>
                </div>
                <div class="summary-row-confirm">
                    <span>Dropping</span>
                    <strong><%= (droppingName == null ? "-" : droppingName) %></strong>
                </div>
                <div class="summary-row-confirm">
                    <span>Fare / Seat</span>
                    <strong>₹ <%= seatPrice %></strong>
                </div>
                <div class="summary-row-confirm">
                    <span>Total Fare</span>
                    <strong>₹ <%= seatCount * seatPrice %></strong>
                </div>

                <form action="<%=request.getContextPath()%>/ConfirmBookingServlet" method="post">
                    <button class="btn" type="submit">Confirm Booking</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>