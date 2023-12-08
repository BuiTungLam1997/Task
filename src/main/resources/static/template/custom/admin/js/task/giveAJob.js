jQuery(function ($) {
    $(document).ready(function () {
        let TaskAPI = "/api/task";
        let TaskURL = "/admin-task-list";
        let init = async () => {
            getData();
        }
        let getData = async (url) => {
            let id = document.getElementById('id').value;
            if (!url) url = `/api/task/get/${id}`;
            $.ajax({
                url: url,
                type: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    if (result.status === '200') {
                        mapTable(result.data);
                    }
                    responseMessage(result.status, result.message);
                },
            });
        }
        let mapTable = (data) => {
            $('#title').val(data.title);
            $('#content').val(data.content);
            $('#status').val(data.status);
        };
        let responseMessage = (status, message) => {
            let row = ``;
            if (status === '200') {
                row += ` <div class="alert alert-success">`;
                row += `<a>${message}</a>`;
                row += `</div>`;
            } else if (status !== '200') {
                row += ` <div class="alert alert-danger">`;
                row += `<a>${message}</a>`;
                row += `</div>`;
            }
            $('#message').empty();
            $('#message').append(row);

        }

        $('#btnSend').click(function (e) {
            e.preventDefault();
            const formData = $('#formSubmitTask').serializeArray();
            const data = {};
            $.each(formData, function (i, v) {
                data["" + v.name + ""] = v.value;
            });
            data["status"] = 'WORKING';
            data["id"] = document.getElementById('id').value;
            update(data);
        });

        function update(data) {
            $.ajax({
                url: TaskAPI + '/giveAJob',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    if (result.status === "200") {
                        window.location.href = TaskURL + '?message=insert_success';
                    } else responseMessage(result.status, result.message);
                },
                error: function (error) {
                    window.location.href = TaskURL + '?message=error_system';
                },
            });
        }

        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
        });

        init();
    });
});