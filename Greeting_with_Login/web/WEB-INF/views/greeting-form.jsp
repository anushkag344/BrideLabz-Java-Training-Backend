<%--
  Created by IntelliJ IDEA.
  User: amanc
  Date: 29-06-2026
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', system-ui, sans-serif;
            background: #0f172a;
            color: #e2e8f0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            background: #1e293b;
            border-radius: 12px;
            border: 1px solid #334155;
            padding: 40px;
            width: 500px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        }
        h2 { color: #38bdf8; margin-bottom: 25px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-size: 14px; color:
                #94a3b8; }
        input[type="text"], input[type="file"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #475569;
            border-radius: 8px;
            background: rgba(15, 23, 42, 0.6);
            color: #fff;
            font-size: 16px;
            outline: none;
        }
        input[type="file"] { cursor: pointer; }
        .img-preview {
            max-width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 8px;
            margin-top: 10px;
            border: 1px solid #475569;
        }
        .actions { display: flex; gap: 15px; margin-top: 30px; }
        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            transition: 0.3s;
        }
        .btn-submit { background: #38bdf8; color: #0f172a; }
        .btn-submit:hover { background: #0ea5e9; }
        .btn-cancel { background: #475569; color: white; }
        .btn-cancel:hover { background: #334155; }
    </style>
</head>
<body>
<div class="container">
    <h2>${title}</h2>
    <!-- enctype="multipart/form-data" is CRITICAL for uploading files -->
    <form action="${pageContext.request.contextPath}/greetings?action=create"
<%--                 "${pageContext.request.contextPath}/${actionUrl}"--%>

          method="POST" enctype="multipart/form-data">
        <!-- Hidden input for existing image path (used in edit mode) -->
        <input type="hidden" name="existingImagePath"
               value="${greeting.imagePath}" />
        <div class="form-group">
            <label>Greeting Message</label>
            <input type="text" name="message" value="${greeting.message}"
                   placeholder="Enter greeting message" required autocomplete="off" />
        </div>
        <div class="form-group">
            <label>Upload Greeting Image</label>
            <input type="file" name="image" accept="image/*" />
            <c:if test="${not empty greeting.imagePath}">
                <label style="margin-top: 15px;">Current Image:</label>
                <img
                        src="${pageContext.request.contextPath}${greeting.imagePath}" class="img-preview"
                />
            </c:if>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-submit">Save
                Greeting</button>
            <a href="${pageContext.request.contextPath}/greetings" class="btn
btn-cancel">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
