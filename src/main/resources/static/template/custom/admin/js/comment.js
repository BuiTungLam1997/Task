jQuery(function ($) {
    $(document).ready(function () {
        let getData = () => {
            let taskId = document.getElementById(`id`).value;
            $.ajax({
                url: '/api/manager/comment/${taskId}',
                type: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    mapData(result.data);
                },
                error: function (error) {

                },
            });
        }
        let mapData = (data) => {
            if (data === null) return;

        }
    })
    $('#btnUpdatePoint').click(function (e) {
        e.preventDefault();
        const data = {};
        data["id"] = document.getElementById(`id`).value;
        data["levelOfDifficulty"] = document.getElementById(`levelOfDifficulty`).value;
        update(data);
    });

    function update(data) {
        $.ajax({
            url: '/api/task-assign/setPoint',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = '/comment?id=' + result.data.id + '&message=update_success';
            },
            error: function (error) {
                window.location.href = '/comment?id=' + data.id + '&message=error_system';
            },
        });
    }
});