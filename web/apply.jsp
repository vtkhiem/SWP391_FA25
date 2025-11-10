<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <form action="${pageContext.request.contextPath}/filterApply" method="get"
              class="d-flex align-items-center gap-2 mt-3 mb-3 px-3">
            <!-- hidden field để truyền Job ID -->
            <input type="hidden" name="jobId" value="${param.jobId}">

            <!-- Search box -->
            <input type="text" class="form-control align-middle"
                   name="txt" id="searchInput" value="${txt}"
                   placeholder="Search by name or email..."
                   style="height: 38px; min-width: 250px;">

            <select class="form-control" name="exp" id="experienceFilter" style="height: 38px; min-width: 180px;">
                <option value="" disabled <c:if test="${empty exp}">selected</c:if> hidden>Filter by Experience</option>
                <option value="0-1" <c:if test="${exp eq '0-1'}">selected</c:if>>0 - 1 years</option>
                <option value="2-3" <c:if test="${exp eq '2-3'}">selected</c:if>>2 - 3 years</option>
                <option value="4-5" <c:if test="${exp eq '4-5'}">selected</c:if>>4 - 5 years</option>
                <option value="5+" <c:if test="${exp eq '5+'}">selected</c:if>>5+ years</option>
                </select>

                <select class="form-control" name="status" id="statusFilter" style="height: 38px; min-width: 150px;">
                    <option value="" disabled <c:if test="${empty status}">selected</c:if> hidden>Filter by Status</option>
                    <option value="Pending">Pending</option>
                    <option value="Approved">Approved</option>
                    <option value="Rejected">Rejected</option>
                </select>

                <button type="submit" class="btn btn-sm btn-warning me-2" style="height:38px;">Apply</button>
                <button type="reset" class="btn btn-sm btn-secondary" style="height:38px;">Clear</button>

            </form>





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
                                                    <select class="form-control status-select"
                                                            data-apply-id="${d.apply.applyId}"
                                                            ${d.apply.status == 'Approved' || d.apply.status == 'Rejected' ? 'disabled' : ''}>
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
                <div class="pagination justify-content-center mt-4">
                    <ul class="pagination">

                        <!-- Nút Prev -->
                        <c:url var="prevUrl" value="filterApply">
                            <c:param name="jobId" value="${param.jobId}" />
                            <c:if test="${not empty param.txt}">
                                <c:param name="txt" value="${param.txt}" />
                            </c:if>
                            <c:if test="${not empty param.exp}">
                                <c:param name="exp" value="${param.exp}" />
                            </c:if>
                            <c:if test="${not empty param.status}">
                                <c:param name="status" value="${param.status}" />
                            </c:if>
                            <c:param name="page" value="${currentPage - 1}" />
                        </c:url>
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${currentPage == 1 ? '#' : prevUrl}">&lt;</a>
                        </li>

                        <!-- Các số trang -->
                        <c:forEach var="i" begin="1" end="${noOfPages}">
                            <c:url var="pageUrl" value="filterApply">
                                <c:param name="jobId" value="${param.jobId}" />
                                <c:if test="${not empty param.txt}">
                                    <c:param name="txt" value="${param.txt}" />
                                </c:if>
                                <c:if test="${not empty param.exp}">
                                    <c:param name="exp" value="${param.exp}" />
                                </c:if>
                                <c:if test="${not empty param.status}">
                                    <c:param name="status" value="${param.status}" />
                                </c:if>
                                <c:param name="page" value="${i}" />
                            </c:url>

                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageUrl}">${i}</a>
                            </li>
                        </c:forEach>

                        <!-- Nút Next -->
                        <c:url var="nextUrl" value="filterApply">
                            <c:param name="jobId" value="${param.jobId}" />
                            <c:if test="${not empty param.txt}">
                                <c:param name="txt" value="${param.txt}" />
                            </c:if>
                            <c:if test="${not empty param.exp}">
                                <c:param name="exp" value="${param.exp}" />
                            </c:if>
                            <c:if test="${not empty param.status}">
                                <c:param name="status" value="${param.status}" />
                            </c:if>
                            <c:param name="page" value="${currentPage + 1}" />
                        </c:url>
                        <li class="page-item ${currentPage == noOfPages ? 'disabled' : ''}">
                            <a class="page-link" href="${currentPage == noOfPages ? '#' : nextUrl}">&gt;</a>
                        </li>

                    </ul>
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
            document.addEventListener("DOMContentLoaded", () => {
                const contextPath = "${pageContext.request.contextPath}";

                // --- 1. FILTER FORM ---
                const form = document.querySelector("form[action$='filterApply']");
                const searchInput = document.getElementById("searchInput");
                const expFilter = document.getElementById("experienceFilter");
                const statusFilter = document.getElementById("statusFilter");

                // Xử lý nút Clear: reset form và reload lại danh sách gốc
                const clearBtn = form.querySelector("button[type='reset']");
                clearBtn.addEventListener("click", (e) => {
                    e.preventDefault();
                    searchInput.value = "";
                    expFilter.value = "";
                    statusFilter.value = "";
                    // Reload lại trang không có filter
                    const jobId = form.querySelector("input[name='jobId']").value;
                });

                // --- 2. CHECKBOX SELECT ALL ---
                const selectAll = document.getElementById("selectAll");
                const checkboxes = document.querySelectorAll(".jobCheckbox");
                if (selectAll) {
                    selectAll.addEventListener("change", () => {
                        checkboxes.forEach(cb => cb.checked = selectAll.checked);
                    });
                    checkboxes.forEach(cb => cb.addEventListener("change", () => {
                            selectAll.checked = Array.from(checkboxes).every(cb => cb.checked);
                        }));
                }

                // --- 3. UPDATE STATUS (AJAX) ---
                document.querySelectorAll(".status-select").forEach(select => {
                    select.addEventListener("change", () => {
                        const applyId = select.dataset.applyId;
                        const newStatus = select.value;

                        fetch(contextPath + "/status", {
                            method: "POST",
                            headers: {"Content-Type": "application/x-www-form-urlencoded"},
                            body: "applyId=" + encodeURIComponent(applyId) +
                                    "&status=" + encodeURIComponent(newStatus)
                        })
                                .then(res => res.text())
                                .then(msg => {
                                    if (msg.trim() !== "")
                                        alert(msg);
                                })
                                .catch(err => console.error("Lỗi cập nhật trạng thái:", err));
                    });
                });

                // --- 4. NOTE MODAL ---
                const noteModal = document.getElementById("noteModal");
                const noteText = document.getElementById("noteText");
                const noteApplyId = document.getElementById("noteApplyId");
                const cancelNoteBtn = document.getElementById("cancelNoteBtn");
                const saveNoteBtn = document.getElementById("saveNoteBtn");
                let currentRow = null;

                document.querySelectorAll(".noteBtn").forEach(btn => {
                    btn.addEventListener("click", (e) => {
                        e.preventDefault();
                        const applyId = btn.dataset.applyId;
                        const row = btn.closest("tr");
                        const currentNote = row.cells[7]?.textContent.trim() || "";

                        noteApplyId.value = applyId;
                        noteText.value = currentNote;
                        noteModal.style.display = "flex";
                        currentRow = row;
                    });
                });

                cancelNoteBtn.addEventListener("click", (e) => {
                    e.preventDefault();
                    noteModal.style.display = "none";
                    currentRow = null;
                });

                saveNoteBtn.addEventListener("click", (e) => {
                    e.preventDefault();
                    const applyId = noteApplyId.value;
                    const newNote = noteText.value;

                    fetch(contextPath + "/noteApply", {
                        method: "POST",
                        headers: {"Content-Type": "application/x-www-form-urlencoded"},
                        body: "applyId=" + encodeURIComponent(applyId) +
                                "&note=" + encodeURIComponent(newNote)
                    })
                            .then(res => res.text())
                            .then(msg => {
                                if (msg.trim() !== "")
                                    alert(msg);
                                if (currentRow)
                                    currentRow.cells[7].textContent = newNote;
                                noteModal.style.display = "none";
                                currentRow = null;
                            })
                            .catch(err => console.error("Lỗi lưu ghi chú:", err));
                });

                // --- 5. DOWNLOAD SELECTED ---
                const downloadBtn = document.getElementById("downloadSelectedBtn");
                if (downloadBtn) {
                    downloadBtn.addEventListener("click", (e) => {
                        e.preventDefault();
                        const selected = Array.from(document.querySelectorAll(".jobCheckbox:checked"))
                                .map(cb => cb.value);
                        if (selected.length === 0) {
                            alert("Vui lòng chọn ít nhất một CV để tải.");
                            return;
                        }
                        window.location.href = contextPath + "/downloadCV?ids=" + selected.join(",");
                    });
                }
            });
        </script>

    </body>
</html>
