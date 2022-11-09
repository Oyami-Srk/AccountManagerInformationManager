function timestamp_to_date_string(timestamp) {
    if (timestamp === null) {
        return "";
    } else {
        let date = new Date(timestamp);
        return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
    }
}

(() => {
    if ($._ajax !== undefined)
        return;
    _ajax = $.ajax;
    $.ajax = function (cfg) {
        let my_callback = null;
        let my_done = (callback) => {
            my_callback = callback;
        }

        let ret = _ajax(cfg);
        let old_done = ret.done;
        ret.done = my_done;
        old_done((resp) => {
            if (typeof (resp) === "object" && resp.status === "FAILED") {
                if (resp.reason === "Permission denied") {
                    window.top.promptAlert(resp.message);
                    return;
                }
            }
            if (my_callback)
                my_callback(resp);
        });
        return ret;
    };
    $._ajax = _ajax;
    console.log("Custom ajax injected.");
})();