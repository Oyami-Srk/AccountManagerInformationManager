// For AMIS
$(document).ready(() => {
    let id = new URLSearchParams(window.location.search).get("id");
    let form = $("form#update-form");
    let api_name = form.attr('name');

    let go_back = () => {
        // window.location.replace(window.location.pathname.replace("Update", ""));
        window.parent.need_refresh = true;
        window.parent.popWin.close();
    }

    let reset = () => {
        $.ajax({
            url: `/api/${api_name}/get`,
            method: "GET",
            data: {
                id
            }
        }).done((resp) => {
            if (resp.status === "OK") {
                let data = resp.data[0];
                for (let input of form.find("ul.forminfo li [name]")) {
                    input = $(input);
                    if (input.attr('type') === "file")
                        continue;
                    let name = input.attr('name');
                    if (name.match(/[Dd]ate$/) !== null)
                        input.val(timestamp_to_date_string(data[name]));
                    else
                        input.val(data[name]);
                }
            } else {
                window.top.promptAlert("记录查询失败！");
                setTimeout(go_back, 1000);
            }
        });
    };
    reset();
    $("input[type='reset']").click(reset);

    form.submit((e) => {
        let old_data = new FormData(e.target);
        let data = new FormData();
        data.append("id", id);
        let files = {};
        for (let [key, value] of old_data.entries()) {
            if (typeof (value) === "object") {
                if (value.size > 0)
                    files[key] = value;
                continue;
            }
            if (key === "icNum" && value.length > 0 && !idcard.validator(value)) {
                window.top.promptAlert("身份证号格式不正确！");
                return false;
            }
            data.set(key, value);
        }

        let do_post = () => {
            $.ajax({
                url: `/api/${api_name}/update`,
                method: "POST",
                data,
                processData: false,
                contentType: false
            }).done((resp) => {
                if (resp.status === "OK") {
                    window.top.promptAlert("修改记录成功！");
                    setTimeout(go_back, 1000);
                } else {
                    window.top.promptAlert("修改记录失败！" + resp.message);
                }
            });
        };

        if (!$.isEmptyObject(files)) {
            let keys = Object.keys(files);
            let t = keys.length;
            for (let key of keys) {
                let file_data = new FormData();
                file_data.append("file", files[key])
                $.ajax({
                    url: "/api/file/upload",
                    method: "POST",
                    data: file_data,
                    processData: false,
                    contentType: false
                }).done((resp) => {
                    if (resp.status === "OK") {
                        let uuid = resp.data;
                        data.append(key, uuid);
                        t--;
                        if (t === 0) {
                            do_post();
                        }
                    } else {
                        window.top.promptAlert("上传文件失败！" + resp.message);
                        return false;
                    }
                });
            }
        } else {
            do_post();
        }
        return false;
    })
});