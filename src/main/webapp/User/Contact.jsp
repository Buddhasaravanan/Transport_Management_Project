<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact | GreenBus</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/BookingPages.css">
    <style>
        .contact-shell {
            width: min(1100px, 92%);
            margin: 28px auto 40px;
        }
        .contact-grid {
            display: grid;
            grid-template-columns: minmax(0, 1.15fr) minmax(280px, 0.85fr);
            gap: 22px;
        }
        .contact-card {
            background: #fff;
            border: 1px solid #e2efe6;
            border-radius: 24px;
            box-shadow: 0 14px 34px rgba(16, 55, 31, 0.08);
            padding: 24px;
        }
        .contact-card h2,
        .contact-card h3 {
            color: #173c27;
        }
        .contact-card p,
        .contact-list li {
            color: #62756b;
            line-height: 1.7;
        }
        .contact-list {
            margin-top: 16px;
            padding-left: 18px;
        }
        .contact-meta {
            display: grid;
            gap: 14px;
            margin-top: 18px;
        }
        .contact-meta .meta-box {
            background: #fbfefd;
            border: 1px solid #e4efe7;
            border-radius: 18px;
            padding: 16px;
        }
        .contact-meta span {
            display: block;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: .8px;
            color: #6a7b71;
            margin-bottom: 6px;
        }
        .contact-meta strong {
            color: #173c27;
        }
        @media (max-width: 860px) {
            .contact-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="contact-shell">
    <div class="page-head">
        <div>
            <p class="section-kicker">Support and help</p>
            <h2>Contact GreenBus</h2>
        </div>
        <span class="page-badge">24x7 booking assistance</span>
    </div>

    <div class="contact-grid">
        <section class="contact-card">
            <h3>We're here to help</h3>
            <p>Reach out for booking support, boarding help, ticket download issues, seat availability questions, or account assistance. Our support team is available throughout the day to help you finish your trip smoothly.</p>
            <ul class="contact-list">
                <li>Help with bus search, seat selection, and booking flow</li>
                <li>Support for PDF ticket download and booking confirmation</li>
                <li>Pickup and dropping point guidance for active routes</li>
                <li>Account login and ticket history support</li>
            </ul>
            <div class="action-bar">
                <a class="primary-btn" href="<%=request.getContextPath()%>/User/home.jsp">Search Buses</a>
                <a class="secondary-btn" href="<%=request.getContextPath()%>/MyTicketsServlet">View My Tickets</a>
            </div>
        </section>

        <aside class="contact-card">
            <h3>Contact details</h3>
            <div class="contact-meta">
                <div class="meta-box">
                    <span>Email</span>
                    <strong>support@greenbus.in</strong>
                </div>
                <div class="meta-box">
                    <span>Phone</span>
                    <strong>+91 98765 43210</strong>
                </div>
                <div class="meta-box">
                    <span>Working hours</span>
                    <strong>24x7 Customer Support</strong>
                </div>
                <div class="meta-box">
                    <span>Coverage</span>
                    <strong>Tamil Nadu and nearby city routes</strong>
                </div>
            </div>
        </aside>
    </div>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>