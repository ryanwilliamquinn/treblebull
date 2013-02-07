function capitaliseFirstLetter(string)
{
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function undefinedOrEmpty(value) {
    return ((typeof value == "undefined") || value.trim() == "")
}

function replacer(key, value) {
    if (key=="$$hashKey") {
        return undefined;
    } else {
        return value;
    }
}