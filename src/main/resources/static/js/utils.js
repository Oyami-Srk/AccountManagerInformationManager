function timestamp_to_date_string(timestamp) {
    if (timestamp === null) {
        return "";
    } else {
        let date = new Date(timestamp);
        return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
    }
}
