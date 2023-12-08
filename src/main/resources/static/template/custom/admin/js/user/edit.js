jQuery(function ($) {
    $(document).ready(function () {
        let init = async () => {
            getData()
        }
        init();
    });
    let APIUrl = `/api/user`;
    let UserGroupAPIUrl = `api/user-group`;
    let UserURL = `/admin-user-list`;
    let getData = (url) => {
        let id = document.getElementById(`id`).value;
        if (!url) url = `/api/user/get/${id}`;
        $.ajax({
            url: url,
            type: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            success: function (result) {
                mapTable(result.data);
                responseMessage(result.status, result.message);
            },
        });
    }

    let responseMessage = (status, message) => {
        let row = ``;
        if (status === '200') {
            row += `<div class="alert alert-success">`;
            row += ` <a>${message}</a>`;
            row += `</div>`;
        } else if (status !== '200') {
            row += `<div class="alert alert-danger">`;
            row += `<a>${message}</a>`;
            row += `</div>`;
        }
        $('#message').empty();
        $('#message').append(row);
    }
    let mapTable = (data) => {
        $('#username').val(data.username);
        $('#password').val(data.password);
        $('#fullName').val(data.fullName);
        $('#status').val(data.status);
        $('#createdBy').val(data.createdBy);
        $('#createdDate').val(data.createdDate);
    };

    $(".alert").delay(2000).slideUp(200, function () {
        $(this).alert('close');
    });

    $('#btnAdd').click(function (e) {
        e.preventDefault();
        const formData = $('#formGroup').serializeArray();
        const data = {};
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        data ['userIds'] = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
    });

    $('#btnAddOrUpdateNew').click(function (e) {
        e.preventDefault();
        const formData = $('#formSubmit').serializeArray();
        const data = {};
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
            update(data);
    });

    function update(data) {
        $.ajax({
            url: APIUrl,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = UserURL + '?&message=update_success';
            },
            error: function (error) {
                window.location.href = UserURL + '?message=error_system';
            },
        });
    }
});