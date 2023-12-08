jQuery(function ($) {
    $(document).ready(function () {
        let init = async () => {
            getData();
            getDataPermission();
        }
        let getData = (url) => {
            let username = document.getElementById('username').value;
            if (!url) url = `/api/admin-home/get/${username}`;
            $.ajax({
                url: url,
                type: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    mapFiled(result.data);
                },
            });
        }
        let getDataPermission = (url) => {
            if (!url) url = `/api/admin-home/getListPermission`;
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
        let mapFiled = (data) => {
            $('#fullName').val(data.fullName);
            $('#status').val(data.status);
            $('#createdBy').val(data.createdBy);
            $('#email').val(data.email);
        };

        let mapTable = (data)=>{
            let row = '';
            $.each(data, function (i, v) {
                row += `<tr>`
                row += `<td>${v.id}</td>`
                row += `<td>${v.name}</td>`
                row += `<td>${v.code}</td>`
                row += `</tr>`
            })
            $('#listPermission').empty();
            $('#listPermission').append(row);
        }
        init();
    });
});