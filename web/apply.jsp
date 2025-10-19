<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="model.ApplyDetail, java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Applications</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/themify-icons.css">
        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/nice-select.css">
        <link rel="stylesheet" href="css/gijgo.css">
        <link rel="stylesheet" href="css/animate.min.css">
        <link rel="stylesheet" href="css/slicknav.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/custom.css"> 

        <style>
            /* ===== BREADCRUMB ===== */
            .bradcam_area {
                background: linear-gradient(135deg, #1489f1 0%, #0f6bb8 100%);
                padding: 4rem 0;
                text-align: center;
                color: white;
            }

            .bradcam_text h3 {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }

            .breadcrumb {
                color: rgba(255, 255, 255, 0.8);
                font-size: 1.1rem;
            }

            .search-box input {
                border-radius: 25px;
                padding-left: 15px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                transition: all 0.3s ease;
            }

            .search-box input:focus {
                border-color: #1489f1;
                box-shadow: 0 0 0 0.2rem rgba(20,137,241,0.25);
            }

            .filter-box select {
                border-radius: 25px;
                padding-left: 10px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                transition: all 0.3s ease;
            }

            .filter-box select:focus {
                border-color: #1489f1;
                box-shadow: 0 0 0 0.2rem rgba(20,137,241,0.25);
            }
        </style>
    </head>
    <body>

        <!-- Header Start -->
        <jsp:include page="header.jsp"/>
        <!-- Header End -->
        <%
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        %>
        <!-- Employer Info -->
        <c:if test="${not empty sessionScope.user and sessionScope.role == 'Employer'}">
            <div class="employer-info" style="padding: 1rem; background-color: #f5f5f5; border-bottom: 1px solid #ddd;">
                <p><strong>Welcome,</strong> ${sessionScope.user.employerName}</p>

            </div>

        </c:if>

        <!-- Breadcrumb Area -->
        <div class="bradcam_area">
            <div class="container">
                <div class="bradcam_text">
                    <h3>Application Management</h3>
                </div>
            </div>
        </div>

        <!-- Search & Filter Controls -->
        <!-- Container -->
        <div class="d-flex align-items-center gap-2 mt-3 mb-3 px-3">
            <!-- Search box -->

            <input type="text" class="form-control align-middle" id="searchInput" placeholder="Search by name or email..." 
                   style="height: 38px; min-width: 250px;">


            <!-- Filters -->
            <select class="form-control" id="experienceFilter" style="height: 38px; min-width: 180px;">
                <option value="">Filter by Experience</option>
                <option value="0-1">0 - 1 years</option>
                <option value="2-3">2 - 3 years</option>
                <option value="4-5">4 - 5 years</option>
                <option value="5+">5+ years</option>
            </select>

            <select class="form-control" id="statusFilter" style="height: 38px; min-width: 150px;">
                <option value="">Filter by Status</option>
                <option value="Pending">Pending</option>
                <option value="Approved">Approved</option>
                <option value="Rejected">Rejected</option>
            </select>

            <button id="clearFilterBtn" class="btn btn-sm btn-warning me-2" style="height:38px;">Clear</button>
            <button id="applyFilterBtn" class="btn btn-sm btn-warning me-2" style="height:38px;">Apply</button> 
        </div>




        <div class="apply_listing_by_job">
            <div class="container-fluid p-0">
                <div class="row">
                    <div class="col-lg-12">
                        <form action="job_delete_bulk" method="post" onsubmit="return confirm('Are you sure want to delete selected jobs?');">
                            <div class="table-responsive mt-3">
                                <table class="table table-hover table-bordered">
                                    <thead class="thead-light">
                                        <tr>
                                            <th style="width:40px">
                                                <input type="checkbox" id="selectAll"/>
                                            </th>
                                            <th style="width:48px">No</th>
                                            <th style="width:160px">Candidate</th>
                                            <th style="width:160px">Email</th>
                                            <th style="width:50px">Experience</th>
                                            <th style="width:60px">Current salary</th>
                                            <th style="width:120px">Status</th>
                                            <th style="width:160px">Note</th>
                                            <th style="width:120px">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="d" items="${details}" varStatus="st">
                                            <tr>
                                                <td>
                                                    <input type="checkbox" name="applyId" value="${d.apply.applyId}" class="jobCheckbox"/>
                                                </td>
                                                <td>${st.index + 1}</td>

                                                <td>
                                                    ${d.cv.fullName}<br>
                                                    <div class="small text-muted" style="margin-top:5px;">
                                                        Applied: ${d.apply.dayCreateFormatted}
                                                    </div>
                                                </td>

                                                <td>${d.cv.email}</td>
                                                <td>${d.cv.numberExp}</td>
                                                <td>${d.cv.currentSalary}</td>
                                                <td>
                                                    <select class="form-control status-select" data-apply-id="${d.apply.applyId}">
                                                        <option value="Pending" ${d.apply.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                                        <option value="Approved" ${d.apply.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                                        <option value="Rejected" ${d.apply.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                    </select>
                                                </td>
                                                <td>${d.apply.note}</td>
                                                <td>
                                                    <div class="d-flex gap-2 ">
                                                        <a class="btn btn-sm btn-warning me-2"
                                                           href="${pageContext.request.contextPath}/${d.cv.fileData}"
                                                           target="_blank">View CV</a>
                                                        <a class="btn btn-sm btn-warning me-2" 
                                                           href="${pageContext.request.contextPath}/downloadCV?ids=${d.apply.applyId}">
                                                            Download CV
                                                        </a>

                                                        <a class="btn btn-sm btn-warning me-2 noteBtn"  
                                                           href="#"
                                                           data-apply-id="${d.apply.applyId}">Note
                                                        </a>
                                                    </div>
                                                </td>

                                            </tr>
                                        </c:forEach>

                                        <c:if test="${empty details}">
                                            <tr>
                                                <td colspan="9" class="text-center">No apply yet.</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </form>
                        <div class="d-flex align-items-center gap-2 mt-3 mb-3 px-3">
                            <a class="btn btn-sm btn-warning me-2" id="downloadSelectedBtn" href="#">
                                Download Selected
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Popup Modal for Note -->
        <div id="noteModal" class="modal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
             background:rgba(0,0,0,0.5); align-items:center; justify-content:center; z-index:1050;">
            <div style="background:#fff; padding:20px; border-radius:8px; width:400px; position:relative;">
                <h5>Edit Note</h5>
                <textarea id="noteText" class="form-control" rows="5" style="width:100%;"></textarea>
                <input type="hidden" id="noteApplyId"/>
                <div class="mt-3 d-flex justify-content-end gap-2">
                    <button id="cancelNoteBtn" class="btn btn-secondary btn-sm">Cancel</button>
                    <button id="saveNoteBtn" class="btn btn-primary btn-sm">Save</button>
                </div>
            </div>
        </div>


        <script>
            // Lấy các input và select
            const searchInput = document.getElementById("searchInput");
            const experienceFilter = document.getElementById("experienceFilter");
            const statusFilter = document.getElementById("statusFilter");
            //function filterTable()


                    // Clear filter
                    const clearFilterBtn = document.getElementById("clearFilterBtn");

            clearFilterBtn.addEventListener("click", () => {
                searchInput.value = "";
                experienceFilter.value = "";
                statusFilter.value = "";
                filterTable(); // gọi lại hàm để reset bảng
            });

            // Chọn tất cả checkbox
            const selectAllCheckbox = document.getElementById("selectAll");
            const rowCheckboxes = document.querySelectorAll(".jobCheckbox");

            selectAllCheckbox.addEventListener("change", function () {
                rowCheckboxes.forEach(checkbox => {
                    checkbox.checked = selectAllCheckbox.checked;
                });
            });

            //update checkbox header
            rowCheckboxes.forEach(checkbox => {
                checkbox.addEventListener("change", function () {
                    // Nếu có checkbox nào chưa tick, header bỏ tick
                    selectAllCheckbox.checked = Array.from(rowCheckboxes).every(cb => cb.checked);
                });
            });

            // Hàm cập nhật trạng thái ứng tuyển
            function updateStatus() {
                const contextPath = "${pageContext.request.contextPath}";

                document.querySelectorAll(".status-select").forEach(select => {
                    select.addEventListener("change", function () {
                        const applyId = this.getAttribute("data-apply-id");
                        const newStatus = this.value;

                        updateApplicationStatus(contextPath, applyId, newStatus);
                    });
                });
            }

            // Hàm gửi yêu cầu cập nhật trạng thái lên server
            function updateApplicationStatus(contextPath, applyId, newStatus) {
                fetch(`${contextPath}/status`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "applyId=" + encodeURIComponent(applyId)
                            + "&status=" + encodeURIComponent(newStatus)
                })
                        .then(response => response.text())
                        .then(data => {
                            console.log("Server response:", data);
                            alert(data);
                        })
                        .catch(error => console.error("Lỗi:", error));
            }

            // Gọi hàm khi trang đã tải xong
            document.addEventListener("DOMContentLoaded", updateStatus);

            const noteModal = document.getElementById("noteModal");
            const noteText = document.getElementById("noteText");
            const noteApplyId = document.getElementById("noteApplyId");
            const cancelNoteBtn = document.getElementById("cancelNoteBtn");
            const saveNoteBtn = document.getElementById("saveNoteBtn");

            // Khi bấm nút Note
            let currentRow = null;

            document.querySelectorAll(".noteBtn").forEach(btn => {
                btn.addEventListener("click", function (e) {
                    e.preventDefault();
                    const applyId = this.getAttribute("data-apply-id");

                    const row = this.closest("tr");  // lấy row hiện tại
                    const currentNote = row.cells[7]?.textContent.trim() || "";

                    noteApplyId.value = applyId;
                    noteText.value = currentNote;
                    noteModal.style.display = "flex";

                    currentRow = row; // lưu row để update sau này
                });
            });

            function saveNote() {
                const applyId = noteApplyId.value;
                const newNote = noteText.value;
                const contextPath = "${pageContext.request.contextPath}";

                fetch(contextPath + "/noteApply", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "applyId=" + encodeURIComponent(applyId)
                            + "&note=" + encodeURIComponent(newNote)
                })
                        .then(response => response.text())
                        .then(data => {
                            if (data.trim() !== "") {
                                alert(data);
                            }

                            // Cập nhật bảng
                            if (currentRow) {
                                currentRow.cells[7].textContent = newNote;
                            }

                            // Đóng modal
                            noteModal.style.display = "none";
                            currentRow = null;
                        })
                        .catch(err => console.error("Lỗi:", err));
            }

            saveNoteBtn.addEventListener("click", function (e) {
                e.preventDefault();
                saveNote();
            });


            // Nút Cancel đóng modal
            cancelNoteBtn.addEventListener("click", (e) => {
                e.preventDefault(); // tránh submit form mặc định
                noteModal.style.display = "none";
                currentRow = null; // reset lại row đang chọn
            });

            // Hàm xử lý tải CV được chọn
            function downloadSelected(event) {
                event.preventDefault();

                const selected = Array.from(document.querySelectorAll(".jobCheckbox:checked"))
                        .map(cb => cb.value);

                if (selected.length === 0) {
                    alert("Vui lòng chọn ít nhất một CV để tải về.");
                    return;
                }

                // Tạo query string ids=1,2,3
                const contextPath = "${pageContext.request.contextPath}";
                const url = "${pageContext.request.contextPath}/downloadCV?ids=" + selected.join(",");
                window.location.href = url; // chuyển hướng đến servlet tải file
            }

            // Gán sự kiện cho nút tải xuống
            document.getElementById("downloadSelectedBtn").addEventListener("click", downloadSelected);

        </script>



    </body>
</html>
