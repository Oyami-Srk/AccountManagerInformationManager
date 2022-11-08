// For AMIS
let total = null;
let total_pages = null;
let count = 5;
let page = 1;
let query_params = {};
let fields = null;
let api_name = null;
let no_select = false;

$(document).ready(() => {
    api_name = $("form#search-form").attr('name');
    fields =
        Array.from($('thead tr#fields th[name]').map((_, e) => $(e).attr('name')));
    no_select = $('thead tr#fields').hasClass('no_select');

    updateTotal();
    fetch_page(page, count);

    $("#add-btn").click(() => {
        window.location.replace(window.location.pathname.replace('.html', 'Add.html'))
    });
    $("#update-btn").click(() => {
        let ids = getSelectedIds();
        if (ids.length > 1) {
            window.top.promptAlert("只能选择一个条目进行修改。");
            return;
        } else if (ids.length === 0) {
            window.top.promptAlert("请选择需要修改的条目。");
            return;
        }
        window.location.replace(window.location.pathname.replace('.html', 'Update.html?id=' + 1))
    });
    $("#delete-btn").click(() => {
        let ids = getSelectedIds();
        if (ids.length === 0) {
            window.top.promptAlert("请选择需要删除的条目。");
            return;
        }
        let t = ids.length;
        for (let id of ids) {
            $.ajax({
                url: `/api/${api_name}/delete`,
                data: {
                    id
                },
                method: "POST"
            }).done((resp) => {
                t--;
                if (t === 0) {
                    window.top.promptAlert("删除成功。");
                    updateTotal();
                    fetch_page(page, count);
                }
            });
        }
    });
    $("#search-form").submit((e) => {
        e.preventDefault();

        let old_data = new FormData($("#search-form")[0]);
        query_params = {};
        for (let [key, value] of old_data.entries()) {
            if (value.length > 0)
                query_params[key] = value;
        }
        page = 1;
        updateTotal();
        fetch_page(page, count);
        return false;
    });
    $("#all-sel").click((e) => {
        let checked = $(e.target).prop('checked');
        for (let input of $("#data").find("input")) {
            $(input).prop('checked', checked);
        }
    });
});

function getAttachment(uuid) {
    let a = $("<a/>");
    $.ajax({
        url: "/api/file/filename",
        method: "GET",
        data: {
            uuid
        }
    }).done((resp) => {
        if (resp.status === "OK") {
            a.text(resp.data);
            a.prop('href', "/api/file/download?uuid=" + uuid);
        }
    });
    return a;
}

function appendRecord(data) {
    let table = $("tbody#data");
    let tr = $("<tr/>", {
        id: data.id
    }).appendTo(table);

    if (!no_select)
        $("<input/>", {
            type: "checkbox"
        }).wrap("<td/>").parent().appendTo(tr);
    for (let field of fields) {
        if (data[field] === null) {
            $("<td/>", {}).appendTo(tr);
            continue;
        }
        let text = data[field].toString();
        // special case
        if (field.match(/[Dd]ate$/) !== null || field === "lastUpdate")
            text = timestamp_to_date_string(data[field]);
        else if ((field === "attachment"
            || $(`table.tablelist th[name=${field}]`).prop('class') === "attachment"
        ) && data[field].length > 0) {
            let a = getAttachment(data[field]);
            a.wrap("<td/>").parent().appendTo(tr);
            continue;
        }
        $("<td/>", {
            text
        }).appendTo(tr);
    }
}

function fetch_page(page, count) {
    let data = {
        last: (page - 1) * count,
        count: count
    };
    data = Object.assign({}, data, query_params);
    $.ajax({
            url: `/api/${api_name}/get`,
            method: "GET",
            data,
        }
    ).done((resp) => {
        $("#page").text(page);
        if (resp.status === "OK") {
            $("#data").empty();
            $("#all-sel").prop('checked', false);
            for (let data of resp.data) {
                appendRecord(data);
            }
        }
    });
}

function updateTotal() {
    $.ajax({
            url: `/api/${api_name}/count`,
            method: "GET",
            data: query_params
        }
    ).done((resp) => {
        if (resp.status === "OK") {
            total = resp.data;
            total_pages = Math.floor(total / count) + ((total % count) === 0 ? 0 : 1);
            $("#count").text(total)
        }
    });
}

function getSelectedIds() {
    return Array.from($("#data")
        .find("input[type='checkbox']:checked")
        .map((_, b) =>
            parseInt($(b).parent().parent().prop('id'))));
}

function firstPage() {
    page = 1;
    fetch_page(page, count);
}

function previousPage() {
    if (page !== 1) {
        page--;
        fetch_page(page, count);
    }

}

function nextPage() {
    if (total != null) {
        if (page !== total_pages) {
            page += 1;
            fetch_page(page, count);
        }
    }
}

function lastPage() {
    if (total != null) {
        page = total_pages;
        fetch_page(page, count);
    }
}

function goPage() {
    let p = parseInt($("#go-page").val());
    if (p > 0 && p <= total_pages) {
        page = p;
        fetch_page(page, count);
    }
}