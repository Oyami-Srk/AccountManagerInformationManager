// For AMIS
$(document).ready(() => {
    let form = $("form#add-form");
    let api_name = form.attr('name');
    form.submit((e) => {
        e.preventDefault();
        let old_data = new FormData(e.target);
        let data = new FormData();
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
                url: `/api/${api_name}/add`,
                method: "POST",
                data,
                processData: false,
                contentType: false
            }).done((resp) => {
                if (resp.status === "OK") {
                    window.parent.promptAlert("添加记录成功！");
                    setTimeout(() => {
                        window.location.replace(window.location.pathname.replace("Add", ""));
                    }, 1000);
                } else {
                    window.parent.promptAlert("添加记录失败！" + resp.message);
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
    });
});