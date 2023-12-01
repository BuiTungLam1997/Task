jQuery(function ($) {
    $(document).ready(function () {
        let APIDeleteTaskFollow = "/api/task-follow/delete";
        let init = async () => {
            getData("");
        }
        let getData = (url) => {
            if (url === "" || url === null) {
                url = `/api/task-follow/list`;
            }
            $.ajax({
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    mapTable(result.data);
                },
            });
        }
        let mapTable = (data) => {
            let row = '';
            $.each(data, function (i, v) {
                row += `<tr>`
                row += `<td><input type="checkbox" id="checkbox_${v.id}" value="${v.id}"></td>`
                row += `<td>${v.title}</td>`
                row += `<td>${v.content}</td>`
                row += `<td>${v.deadlineStart}</td>`
                row += `<td>${v.deadlineEnd}</td>`
                row += `<td>${v.status}</td>`
                row += `</tr>`
            })
            $('#listTaskFollow').empty();
            $('#listTaskFollow').append(row);
        }
        $('#btnUnFollow').click(function (e) {
            warningBeforeDelete();
        });

        function warningBeforeDelete() {
            swal({
                title: "Mài có chắc chắn xóa nó không ?",
                text: "Thấy câu hỏi ở trên không ,ừ chỗ này giống nó đó ,trả lời đi!",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "btn-success",
                cancelButtonClass: "btn-danger",
                confirmButtonText: "Có, Con đồng ý xóa thưa ngài!",
                cancelButtonText: "Không , Con cần thời gian suy nghĩ!",
                closeOnConfirm: false,
                closeOnCancel: false
            }).then(function (isConfirm) {
                if (isConfirm) {
                    unFollow();
                }
            });
        }

        let unFollow = () => {
            let data = {};
            data ['taskIds'] = $('tbody input[type=checkbox]:checked').map(function () {
                return $(this).val();
            }).get();
            $.ajax({
                url: APIDeleteTaskFollow,
                type: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (result) {
                    responseMessage(result.message, result.status);
                },
                error: function (error) {
                    responseMessage("Error!", "400");
                },
            });
        }

        let responseMessage = (message, status) => {
            let row = ``;
            if (status === '200') {
                row += `<div class="alert alert-success">`;
                row += ` <a>message</a>`;
                row += `</div>`;
            } else if (status !== '200') {
                row += `<div class="alert alert-danger">`;
                row += `<a>${message}</a>`;
                row += `</div>`;
            }
            $('#message').empty();
            $('#message').append(row);

        }

        $(".alert").delay(2000).slideUp(200, function () {
            $(this).alert('close');
        });
        init();

    })
});