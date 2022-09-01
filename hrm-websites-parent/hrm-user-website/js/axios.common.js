axios.interceptors.request.use(config => {
    //从cookie中获取token
    let token = $.cookie('U-TOKEN');
    if (token && token.length > 0) {
        //将token信息放到请求头中
        config.headers['Authorization'] = "Bearer " + token;
    }
    return config
}, error => {
    Promise.reject(error)
})