axios.interceptors.request.use(config => {
    var cookie = document.cookie;   //token=xxxxxxxx
    var token;
    if (cookie.indexOf("token") >= 0){
        token = cookie.split("=")[1];
    }
    //如果已经登录了,每次都把token作为一个请求头传递过程
    if (token && token.length > 0) {

        // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
        config.headers['X-Token'] = token;
    }
    return config
}, error => {
    Promise.reject(error)
})