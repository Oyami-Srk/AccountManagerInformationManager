// For AMIS
let total = null;
let total_pages = null;
let count = 5;
let page = 1;
let query_params = {};
let fields = null;
let api_name = null;
let no_select = false;
let need_refresh = false;

let paging_div = `
<div class="pagin">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
             <td class="STYLE4">
                 <div class="message">共<i class="blue" id="count"></i>条记录，当前显示第<i class="blue"
                                                                                           id="page"></i>页
                 </div>
             </td>
             <td>
                 <table border="0" align="right" cellpadding="0" cellspacing="0">
                     <tr>
                         <td width="45"><img src="../../images/first.gif" width="33" height="20"
                                             style="cursor:hand" onclick="firstPage()"/></td>
                         <td width="50"><img src="../../images/back.gif" width="43" height="20"
                                             style="cursor:hand" onclick="previousPage()"/></td>
                         <td width="50"><img src="../../images/next.gif" width="43" height="20"
                                             style="cursor:hand" onclick="nextPage()"/></td>
                         <td width="40"><img src="../../images/last.gif" width="33" height="20"
                                             style="cursor:hand" onclick="lastPage()"/></td>
                         <td width="100">
                             <div align="center"><span class="STYLE1">转到第
	             <input name="textfield" type="text" size="4"
                        style="height:16px; width:35px; border:1px solid #999999;" id="go-page"/>
	             页 </span></div>
                         </td>
                         <td width="40"><img src="../../images/go.gif" width="33" height="17" style="cursor:hand"
                                             onclick="goPage()"/></td>
                     </tr>
                 </table>
             </td>
        </tr>
    </table>
</div>`;

$(document).ready(() => {
    api_name = $("form#search-form").attr('name');
    fields =
        Array.from($('thead tr#fields th[name]').map((_, e) => $(e).attr('name')));
    no_select = $('thead tr#fields').hasClass('no_select');

    updateTotal();
    fetch_page(page, count);

    $("#add-btn").click(() => {
        // window.location.replace(window.location.pathname.replace('.html', 'Add.html'));
        let uri = window.location.pathname.replace('.html', 'Add.html');
        popWin.showWin(uri, 1000, 600, function () {
            if (need_refresh) {
                updateTotal();
                fetch_page(page, count);
                need_refresh = false;
            }
        });
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
        let uri = window.location.pathname.replace('.html', 'Update.html?id=' + ids[0]);
        popWin.showWin(uri, 1000, 600, function () {
            if (need_refresh) {
                updateTotal();
                fetch_page(page, count);
                need_refresh = false;
            }
        });
    });
    $("#delete-btn").click(() => {
        let ids = getSelectedIds();
        if (ids.length === 0) {
            window.top.promptAlert("请选择需要删除的条目。");
            return;
        }
        doPostForIds(ids, "delete", "删除");
        updateTotal();
        fetch_page(page, count);
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
    let all_sel = $("#all-sel");
    if (all_sel.length > 0) {
        all_sel.parent().click((e) => {
            let checked = all_sel.prop('checked');
            if (e.target.getAttribute('type') === "checkbox") {
                checked = !checked;
            }
            all_sel.prop('checked', !checked);
            for (let input of $("#data").find("input")) {
                $(input).prop('checked', !checked);
            }
        });
    }
    $(".rightinfo").append(paging_div);
});

function setRefresh(r) {
    need_refresh = r;
}

function doPostForIds(ids, api, what, ext_data = null) {
    let t = ids.length;
    let s = 0;
    let f = 0;
    let err = [];
    data = {};
    if (ext_data !== null) {
        data = ext_data;
    }
    for (let id of ids) {
        data['id'] = id;
        console.log(data);
        $.ajax({
            url: `/api/${api_name}/${api}`,
            data,
            method: "POST"
        }).done((resp) => {
            t--;
            if (resp.status === "FAILED") {
                f++;
                err.push(resp.message);
            } else {
                s++;
            }
            if (t === 0) {
                if (f !== 0 && s === 0)
                    window.top.promptAlert(what + "失败。" + [...new Set(err)].join('，'));
                else if (f === 0) {
                    window.top.promptAlert(what + "成功。");
                } else {
                    window.top.promptAlert(what + "成功" + s.toString() + "个条目。错误原因：" + [...new Set(err)].join('，'));
                }
                updateTotal();
                fetch_page(page, count);
            }
        });
    }
}

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

    if (!no_select) {
        let td = $("<input/>", {
            type: "checkbox"
        }).wrap("<td/>").parent().appendTo(tr);
        td.click((e) => {
            if (e.target.getAttribute('type') === "checkbox")
                return;
            let cb = tr.find("input[type='checkbox']");
            cb.prop('checked', !cb.prop('checked'));
        });
    }
    for (let field of fields) {
        if (data[field] === null) {
            $("<td/>", {}).appendTo(tr);
            continue;
        }
        let text = data[field].toString();
        // special case
        if (field.match(/[Tt]ime$/) !== null
            || field.match(/[Dd]ate$/) !== null
            || field === "lastUpdate"
            || field === "birthday")
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
    return {
        tbody: table,
        tr: tr
    };
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