const rest = require('rest');
const mime = require('rest/interceptor/mime');
export const client = rest.wrap(mime);

export const serverAddress = "http://localhost:8080/";