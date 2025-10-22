<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh toán VNPay</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #ffffff;
            padding: 2em;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 1.5em;
        }
        .form-group {
            margin-bottom: 1.5em;
        }
        label {
            display: block;
            margin-bottom: 0.5em;
            color: #555;
            font-weight: bold;
        }
        input[type="number"],
        input[type="text"] {
            width: 100%;
            padding: 0.8em;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }
        input[type="number"]:focus,
        input[type="text"]:focus {
            border-color: #007bff;
            outline: none;
        }
        input[type="submit"] {
            width: 100%;
            padding: 1em;
            background-color: #007bff;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Thanh toán qua VNPay</h2>
        <form action="payment" method="post">
            <div class="form-group">
                <label for="amount">Số tiền (VND):</label>
                <input type="number" id="amount" name="amount" value="100000" required>
            </div>
            <div class="form-group">
                <label for="orderInfo">Thông tin đơn hàng:</label>
                <input type="text" id="orderInfo" name="orderInfo" value="Thanh toán đơn hàng" required>
            </div>
            <input type="submit" value="Thanh toán">
        </form>
    </div>
</body>
</html>