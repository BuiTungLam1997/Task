jQuery(function ($) {
    $(document).ready(function () {
        let UserAPIURL = "/api/user";
        let ListURL = "/admin-user-list";
        let UserURL = "/admin-user-edit";
        $('#btnAdd').click(function (e) {
            e.preventDefault();
            const formData = $('#formSubmit').serializeArray();
            const data = {};
            $.each(formData, function (i, v) {
                data["" + v.name + ""] = v.value;
            });
            create(data);
        });

        function create(data) {
            $.ajax({
                url: UserAPIURL+`/create`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = UserURL + "?id=" + result.id + "&message=insert_success";
                },
                error: function (error) {
                    window.location.href = ListURL + '?message=error_system';
                },
            });
        }
    });
});