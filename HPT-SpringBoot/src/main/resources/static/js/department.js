// Call API show department
let departmentCallAPIShow = $(document).ready(function() {
    $.ajax({
        url: "/api/department",
        method: "GET",
        success: function(data) {
            let tableBody = $("#departmentTable tbody");
            tableBody.empty();
            data.forEach(function(department) {
                let row = "<tr>" +
                    "<td>" + department.department_id + "</td>" +
                    "<td>" + department.department_name + "</td>" +
                    "<td><a href='/department/update/" + department.department_id + "'><button type='button' class='btn btn-danger'>Update</button></a></td>" +
                    "<td><button onclick='deleteDepartment( + department.department_id + )' type='button' class='btn btn-danger'>Delete</button></td>" +
                    "</tr>";
                tableBody.append(row);
            });
        },
        error: function(error) {
            console.log("Error fetching departments", error);
        }
    });
});

// Call API update department
let departmentCallAPIUpdate = $(document).ready(function () {
    $("#updateDepartmentForm").submit(function (event) {
        event.preventDefault();
        let department = {
            department_id: $("#department_id").val(),
            department_name: $("#department_name").val()
        };

        /* Send AJAX request to update department */
        $.ajax({
            url: "/api/department/" + department.department_id, // URL of the API to update a department
            method: "PUT", // Method
            contentType: "application/json", // Type json
            data: JSON.stringify(department), // Convert department object to json
            success: function (response) {
                alert("Department updated successfully!");
                window.location.href = "/department/"; // Redirect the user to the department list page
            },
            error: function (error) {
                console.log("Error updating department", error);
            }
        });
    });
});

let departmentCallAPIDelete = function deleteDepartment(id) {
    if (confirm("Are you sure you want to delete this department?")) {

        /* Send AJAX request to delete department */
        $.ajax({
            url: "/api/department/" + id, // URL of to API delete department
            method: "DELETE", // Method
            success: function (result) {
                alert("Department deleted successfully!");
                location.reload();
            },
            error: function (error) {
                console.log("Error deleting department", error);
            }
        });
    }
}
