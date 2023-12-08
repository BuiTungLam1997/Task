jQuery(function ($) {
    $(document).ready(function () {
        let APIUrl = `/api/user`;
        let UserGroupAPIUrl = `api/user-group`;
        let UserURL = `/admin-user-list`;

        $('#btnChange').click(function (e) {
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
                url: APIUrl + `/change`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    responseMessage(result.status, result.message);
                },
                error: function (error) {
                    responseMessage(`400`, `Error!`);
                    ;
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
    });
});