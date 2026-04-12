<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    List<String[]> seats = (List<String[]>) request.getAttribute("seats");
    Double seatPrice = (Double) request.getAttribute("seatPrice");
    int scheduleId = (int) request.getAttribute("scheduleId");

    if(seatPrice == null) seatPrice = 0.0;
%>

<!DOCTYPE html>
<html>
<head>
<title>Select Seat</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Seats.css">

<script>
let selectedSeats=[];
let seatPrice=<%=seatPrice%>;

function toggleSeat(seatNo){
    const seat=document.getElementById("seat-"+seatNo);
    if(seat.classList.contains("booked")) return;

    if(seat.classList.contains("selected")){
        seat.classList.remove("selected");
        selectedSeats=selectedSeats.filter(s=>s!==seatNo);
    }else{
        seat.classList.add("selected");
        selectedSeats.push(seatNo);
    }

    document.getElementById("seatCount").innerText=selectedSeats.length;
    document.getElementById("totalFare").innerText=
        selectedSeats.length*seatPrice;

    document.getElementById("seatCountBottom").innerText=
        selectedSeats.length;
    document.getElementById("totalFareBottom").innerText=
        selectedSeats.length*seatPrice;
}

function openBoardingModal(){

    if(selectedSeats.length === 0){
        alert("Please select at least one seat");
        return;
    }

    let scheduleId = document.getElementById("scheduleId").value;

    window.location.href =
        "BoardingServlet?scheduleId=" + scheduleId +
        "&seatNos=" + selectedSeats.join(",");
}
</script>

</head>
<body>

<jsp:include page="header.jsp" />

<input type="hidden" id="scheduleId" value="<%=scheduleId%>">

<div class="rb-page-shell">
    <div class="rb-steps">
        <span class="active">1. Select seats</span>
        <span>2. Boarding & dropping</span>
        <span>3. Passenger info</span>
        <span>4. Confirm booking</span>
    </div>

    <div class="rb-container">

        <div class="bus-structure-card">
            <div class="seat-header">
                <div>
                    <p class="section-kicker">Choose your preferred seats</p>
                    <h2>Select seats for your trip</h2>
                </div>
                <div class="seat-legend">
                    <span><i class="box available"></i> Available</span>
                    <span><i class="box selected"></i> Selected</span>
                    <span><i class="box booked"></i> Booked</span>
                </div>
            </div>

            <div class="bus-structure">
                <div class="driver-area">🛞 Driver</div>

                <div class="seat-layout">

                    <%
                    int col = 0;
                    for(String[] s:seats){
                        String seatNo=s[0];
                        String status=s[1];

                        if(col==2){
                    %>
                        <div class="aisle"></div>
                    <%
                        }
                    %>

                    <div class="seat-column">
                        <div
                            id="seat-<%=seatNo%>"
                            class="seat <%=status.equals("BOOKED")?"booked":"available"%>"
                            <% if(!status.equals("BOOKED")){ %>
                                onclick="toggleSeat('<%=seatNo%>')"
                            <% } %>
                        >
                            <span class="seat-number"><%=seatNo%></span>

                            <% if(status.equals("LADIES")){ %>
                                <span class="ladies-icon">♀</span>
                            <% } %>

                        </div>
                        <div class="seat-price">₹<%=seatPrice%></div>
                    </div>

                    <%
                        col++;
                        if(col==4){
                            col = 0;
                        }
                    }

                    if(col>0){
                        if(col<=2){
                            for(int i=col; i<2; i++){
                    %>
                        <div class="seat-column"></div>
                    <%
                            }
                    %>
                        <div class="aisle"></div>
                    <%
                            for(int i=0; i<2; i++){
                    %>
                        <div class="seat-column"></div>
                    <%
                            }
                        }else{
                            for(int i=col; i<4; i++){
                    %>
                        <div class="seat-column"></div>
                    <%
                            }
                        }
                    }
                    %>

                </div>
            </div>
        </div>

        <aside class="summary-card">
            <h3>Trip Summary</h3>
            <div class="summary-line">
                <span>Seats Selected</span>
                <b id="seatCount">0</b>
            </div>
            <div class="summary-line">
                <span>Fare / Seat</span>
                <b>₹ <%=seatPrice%></b>
            </div>
            <div class="summary-line total-line">
                <span>Total Fare</span>
                <b>₹ <span id="totalFare">0</span></b>
            </div>

            <button class="continue-btn" onclick="openBoardingModal()">
                Select Boarding & Dropping
            </button>
        </aside>

    </div>
</div>

<div class="bottom-bar">
    <div>
        <b id="seatCountBottom">0</b> seat(s) |
        ₹ <b id="totalFareBottom">0</b>
    </div>

    <button class="continue-btn" onclick="openBoardingModal()">
        Continue
    </button>
</div>

</body>
</html>