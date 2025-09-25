<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Detail - Job Board</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- CSS: Bootstrap first, then libraries, then main style -->
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
    </head>

    <body>
        <!-- header_start -->
        <jsp:include page="employer-header.jsp"/>
        <!-- header_end -->

        <!-- bradcam_area -->  
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>Edit Job</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area-->  

        <div class="job_details_area">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8">
                        <!-- Form edit job -->
                        <div class="white-bg p-4">
                            <h4>Edit Job Information</h4>
                            <!-- Toast Notification -->
                            <c:if test="${not empty message}">
                                <div class="toast-message success">${message}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="toast-message error">${error}</div>
                            </c:if>
                            <form action="job_edit" method="post">
                                <input type="hidden" name="id" value="${job.jobPostID}"/>
                                <input type="hidden" name="employerId" value="${sessionScope.employer.employerID}"/>

                                <div class="form-group">
                                    <label>Job Title</label>
                                    <input type="text" class="form-control" name="title" value="${job.title}" required>
                                </div>

                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea class="form-control" name="description" rows="5">${job.description}</textarea>
                                </div>

                                <div class="form-group">
                                    <label>Category</label>
                                    <select name="category" required>
                                        <option value="">-- Select Category --</option>
                                        <option value="IT">IT</option>
                                        <option value="Marketing">Marketing</option>
                                        <option value="Finance">Finance</option>
                                        <option value="Human Resources">Human Resources</option>
                                        <option value="Design">Design</option>
                                        <option value="Education">Education</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Position</label>
                                    <input type="text" class="form-control" name="position" value="${job.position}">
                                </div>

                                <div class="form-group">
                                    <label>Location</label>
                                    <select name="location" id="locationSelect" required>
                                        <option value="">-- Select Location --</option>
                                        <option value="Hà Nội">Hà Nội</option>
                                        <option value="Hồ Chí Minh">Hồ Chí Minh</option>
                                        <option value="Hải Phòng">Hải Phòng</option>
                                        <option value="Đà Nẵng">Đà Nẵng</option>
                                        <option value="Cần Thơ">Cần Thơ</option>
                                        <option value="An Giang">An Giang</option>
                                        <option value="Bà Rịa - Vũng Tàu">Bà Rịa - Vũng Tàu</option>
                                        <option value="Bắc Giang">Bắc Giang</option>
                                        <option value="Bắc Kạn">Bắc Kạn</option>
                                        <option value="Bạc Liêu">Bạc Liêu</option>
                                        <option value="Bắc Ninh">Bắc Ninh</option>
                                        <option value="Bến Tre">Bến Tre</option>
                                        <option value="Bình Định">Bình Định</option>
                                        <option value="Bình Dương">Bình Dương</option>
                                        <option value="Bình Phước">Bình Phước</option>
                                        <option value="Bình Thuận">Bình Thuận</option>
                                        <option value="Cà Mau">Cà Mau</option>
                                        <option value="Cao Bằng">Cao Bằng</option>
                                        <option value="Đắk Lắk">Đắk Lắk</option>
                                        <option value="Đắk Nông">Đắk Nông</option>
                                        <option value="Điện Biên">Điện Biên</option>
                                        <option value="Đồng Nai">Đồng Nai</option>
                                        <option value="Đồng Tháp">Đồng Tháp</option>
                                        <option value="Gia Lai">Gia Lai</option>
                                        <option value="Hà Giang">Hà Giang</option>
                                        <option value="Hà Nam">Hà Nam</option>
                                        <option value="Hà Tĩnh">Hà Tĩnh</option>
                                        <option value="Hải Dương">Hải Dương</option>
                                        <option value="Hậu Giang">Hậu Giang</option>
                                        <option value="Hòa Bình">Hòa Bình</option>
                                        <option value="Hưng Yên">Hưng Yên</option>
                                        <option value="Khánh Hòa">Khánh Hòa</option>
                                        <option value="Kiên Giang">Kiên Giang</option>
                                        <option value="Kon Tum">Kon Tum</option>
                                        <option value="Lai Châu">Lai Châu</option>
                                        <option value="Lâm Đồng">Lâm Đồng</option>
                                        <option value="Lạng Sơn">Lạng Sơn</option>
                                        <option value="Lào Cai">Lào Cai</option>
                                        <option value="Long An">Long An</option>
                                        <option value="Nam Định">Nam Định</option>
                                        <option value="Nghệ An">Nghệ An</option>
                                        <option value="Ninh Bình">Ninh Bình</option>
                                        <option value="Ninh Thuận">Ninh Thuận</option>
                                        <option value="Phú Thọ">Phú Thọ</option>
                                        <option value="Phú Yên">Phú Yên</option>
                                        <option value="Quảng Bình">Quảng Bình</option>
                                        <option value="Quảng Nam">Quảng Nam</option>
                                        <option value="Quảng Ngãi">Quảng Ngãi</option>
                                        <option value="Quảng Ninh">Quảng Ninh</option>
                                        <option value="Quảng Trị">Quảng Trị</option>
                                        <option value="Sóc Trăng">Sóc Trăng</option>
                                        <option value="Sơn La">Sơn La</option>
                                        <option value="Tây Ninh">Tây Ninh</option>
                                        <option value="Thái Bình">Thái Bình</option>
                                        <option value="Thái Nguyên">Thái Nguyên</option>
                                        <option value="Thanh Hóa">Thanh Hóa</option>
                                        <option value="Thừa Thiên Huế">Thừa Thiên Huế</option>
                                        <option value="Tiền Giang">Tiền Giang</option>
                                        <option value="Trà Vinh">Trà Vinh</option>
                                        <option value="Tuyên Quang">Tuyên Quang</option>
                                        <option value="Vĩnh Long">Vĩnh Long</option>
                                        <option value="Vĩnh Phúc">Vĩnh Phúc</option>
                                        <option value="Yên Bái">Yên Bái</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Salary Range</label>
                                    <div class="d-flex">
                                        <input type="number" class="form-control mr-2" name="offerMin" value="${job.offerMin}">
                                        <input type="number" class="form-control" name="offerMax" value="${job.offerMax}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Years of Experience</label>
                                    <select name="numberExp" id="expSelect" required>
                                        <option value="0">No Exp</option>
                                        <option value="1">1 year</option>
                                        <option value="2">2 years</option>
                                        <option value="3">3 years</option>
                                        <option value="5">5 years</option>
                                        <option value="10">10+ years</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Job Type</label>
                                    <select name="typeJob">
                                        <option value="Full-time">Full-time</option>
                                        <option value="Part-time">Part-time</option>
                                        <option value="Internship">Internship</option>
                                    </select>
                                </div>

                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                    <a href="job_details?id=${job.jobPostID}" class="btn btn-secondary">Cancel</a>
                                </div>
                            </form>
                        </div>

                        <!-- Delete job -->
                        <div class="white-bg p-4 mt-3">
                            <h4>Danger Zone</h4>
                            <form action="job_delete" method="post" onsubmit="return confirm('Are you sure you want to delete this job?');">
                                <input type="hidden" name="id" value="${job.jobPostID}"/>
                                <button type="submit" class="btn btn-danger">Delete Job</button>
                            </form>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <!-- Job summary -->
                        <div class="job_sumary white-bg p-3">
                            <h3>Job Summary</h3>
                            <ul>
                                <li>Published on: <span>${job.dayCreate}</span></li>
                                <li>Category: <span>${job.category}</span></li>
                                <li>Salary: <span>${job.offerMin} - ${job.offerMax}</span></li>
                                <li>Location: <span>${job.location}</span></li>
                                <li>Job Nature: <span>${job.typeJob}</span></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->

        <script>
            // Validate form
            function validateForm() {
                const min = parseFloat(document.querySelector("[name='offerMin']").value);
                const max = parseFloat(document.querySelector("[name='offerMax']").value);
                if (min > max) {
                    alert("Minimum salary cannot be greater than maximum salary!");
                    return false;
                }
                return true;
            }

            // Toast notification
            document.addEventListener("DOMContentLoaded", function () {
                const toasts = document.querySelectorAll(".toast-message");
                toasts.forEach((toast, index) => {
                    setTimeout(() => {
                        toast.style.right = "20px";
                    }, 200 + index * 100);
                    setTimeout(() => {
                        toast.style.right = "-350px";
                        toast.style.opacity = "0";
                    }, 4000 + index * 100);
                });

                // Set selected option
                document.getElementById("categorySelect").value = "${job.category}";
                document.getElementById("locationSelect").value = "${job.location}";
                document.getElementById("expSelect").value = "${job.numberExp}";
                document.getElementById("typeJobSelect").value = "${job.typeJob}";
            });
        </script>
    </body>
</html>