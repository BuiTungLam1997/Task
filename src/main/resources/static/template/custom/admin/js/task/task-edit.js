jQuery(function ($) {
    $(document).ready(function () {
        let TaskAPIURL = "/api/task";
        let ListURL = "/admin-task-list?page=1&limit=4";
        let EditURL = "/admin-task-edit";
        let init = async () => {
            await getData("")
        }


        let getData = async (url) => {
            let id = document.getElementById('id').value;
            if (url === "" || url === null) url = `/api/task/edit/${id}`;
            $.ajax({
                url: url,
                type: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    mapTable(result.data);
                },
            });
        }

        let mapTable = (data) => {
            $('#idResponse').val(data.id);
            $('#title').val(data.title);
            $('#content').val(data.content);
            $('#performer').val(data.performer);
            $('#note').val(data.note);
            $('#levelOfDifficulty').val(data.levelOfDifficulty);
            $('#deadlineStart').val(data.deadlineStart);
            $('#deadlineEnd').val(data.deadlineEnd);

        };

        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
        });

        $('#btnAddOrUpdateNew').click(function (e) {
            e.preventDefault();
            const formData = $('#formSubmit').serializeArray();
            const data = {};
            $.each(formData, function (i, v) {
                data["" + v.name + ""] = v.value;
            });
            const id = $('#id').val();


            if (id === "") {
                create(data);
            } else {
                update(data);
            }
        });

        function create(data) {
            $.ajax({
                url: TaskAPIURL,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = ListURL + '&message=insert_success';
                },
                error: function (error) {
                    window.location.href = EditURL + '?message=error_system';
                },
            });
        }

        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
        });

        function update(data) {
            $.ajax({
                url: TaskAPIURL,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = ListURL + '&message=update_success';
                },
                error: function (error) {
                    window.location.href = ListURL + '&message=error_system';
                },
            });
        }

        init();
    });
});