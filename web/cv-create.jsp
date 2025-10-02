<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!doctype html>
    <html class="no-js" lang="zxx">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>CV Create</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- CSS here -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/themify-icons.css">
        <link rel="stylesheet" href="css/nice-select.css">
        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/gijgo.css">
        <link rel="stylesheet" href="css/slicknav.css">
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <!-- header-start -->
        <jsp:include page="header.jsp"/>
        <!-- header-end -->

        <!-- bradcam_area  -->
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>Create Your CV</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area  -->

        <!-- Add this after the bradcam_area div and before the form -->
        <div class="container mt-3">
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <% if(request.getAttribute("message") != null) { %>
                <div class="alert alert-success" role="alert">
                    <%= request.getAttribute("message") %>
                </div>
            <% } %>
        </div>

        <!-- CV creation form area -->
        <!-- CV creation form area -->
        <div class="container">
            <div class="row">
                <div class="col-12 my-5">
                    <form action="${pageContext.request.contextPath}/create-cv" method="post" enctype="multipart/form-data">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Full Name</label>
                                    <input type="text" class="form-control" name="fullName" required>
                                </div>

                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" name="email" required>
                                </div>

                                <div class="form-group">
                                    <label>Address</label>
                                    <input type="text" class="form-control" name="address" required>
                                </div>

                                <div class="form-group">
                                    <label>Position Applied For</label>
                                    <input type="text" class="form-control" name="position" required>
                                </div>

                                <div class="form-group">
                                    <label>Years of Experience</label>
                                    <input type="number" class="form-control" name="numberExp" min="0" required>
                                </div>

                                <div class="form-group">
                                    <label>Field</label>
                                    <input type="text" class="form-control" name="field" required>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Current Salary (USD)</label>
                                    <input type="number" class="form-control" name="currentSalary" min="0" required>
                                </div>

                                <div class="form-group">
                                    <label>Birthday</label>
                                    <input type="date" class="form-control" name="birthday" required>
                                </div>

                                <div class="form-group">
                                    <label>Nationality</label>
                                    <input type="text" class="form-control" name="nationality" required>
                                </div>

                                <div class="form-group">
                                    <label>Gender</label>
                                    <select class="form-control" name="gender" required>
                                        <option value="">Select Gender</option>
                                        <option value="male">Male</option>
                                        <option value="female">Female</option>
                                        <option value="other">Other</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Education</label>
                                    <textarea class="form-control" name="education" rows="4" required></textarea>
                                </div>

                                <div class="form-group">
                                    <label>CV File Upload</label>
                                    <input type="file" class="form-control" name="cvFile" accept=".pdf,.doc,.docx"
                                        required>
                                    <small class="form-text text-muted">Accepted formats: PDF, DOC, DOCX</small>
                                </div>
                            </div>
                        </div>

                        <button type="submit" class="boxed-btn3 w-100 mt-4">Create CV</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- footer start -->
        <footer class="footer">
            <!-- ... (footer content similar to blog.html) ... -->
        </footer>
        <!--/ footer end  -->

        <!-- JS here -->

    </body>

    </html>