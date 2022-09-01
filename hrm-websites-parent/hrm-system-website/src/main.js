import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import 'element-ui/lib/theme-default/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'
import routes from './routes'
// import Mock from './mock'
// Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'

import axios from 'axios'

//响应拦截器
axios.interceptors.response.use(config => {
    return config
},error => {
    if (error && error.response) {
        console.log("===================================");
        console.log(error);
        switch (error.response.status) {
            case 401: return doRequest(error); //token过期了
            case 403: return doNoPerHandler(error); //token没过期，但是没有权限访问
        }
    }
    Promise.reject(error)
});
function doNoPerHandler(error) {
    alert("没访问权限");
}
//这里必须是同步请求
async function doRequest (error) {
    try {
        //获取新的Token
        const tokenData = await getNewToken();
        var token = tokenData.data.data.accessToken;
        var refresh_token = tokenData.data.data.refreshToken;
        localStorage.setItem("U-TOKEN", token);
        localStorage.setItem("R-TOKEN", refresh_token);
        //继续执行上一次失败的请求
        const res = await axios.request(error.config);
        return res;
    } catch(err) {
        alert("登录失效,请重新登录");
        localStorage.clear();
        router.replace({ path:"/login" });
        return err;
    }
}

//刷新Token
async function getNewToken() {
    var refreshToken = localStorage.getItem('R-TOKEN');
    if(refreshToken){
        return await axios({
            url: '/auth/login/refreshToken?refreshToken=' + refreshToken+"&type=1",
            method: 'post',
            headers: {
                'Content-Type':'application/x-www-form-urlencoded'
            }
        })
    }else{
        alert("登录失效,请重新登录");
        localStorage.clear();
        router.replace({ path:"/login" });
    }
}

//axios前置拦截器，每次请求都会走这里
axios.interceptors.request.use(config => {
    //如果已经登录了,每次都把token作为一个请求头传递过程
    if (localStorage.getItem('U-TOKEN')) {
        // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
        config.headers['Authorization'] = "Bearer " + localStorage.getItem('U-TOKEN');
    }
    console.debug('config',config)
    return config
}, error => {
    // Do something with request error
    Promise.reject(error)
});

//配置axios的全局基本路径
axios.defaults.baseURL='http://localhost:1020/hrm';
//全局属性配置，在任意组件内可以使用this.$http获取axios对象
Vue.prototype.$http = axios

import BaiduMap from 'vue-baidu-map'

Vue.use(BaiduMap, {
    // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
    ak: 'TvDzvZNeapR0NfFRHj7ejjB4odWSH51O'
})
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

//NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes
})

//前置拦截器（每次发送请求之前，会执行该方法）每次路由之前都要执行,每次请求都要经过路由
router.beforeEach((to, from, next) => {
    console.log("********************");
    console.log("to.path=" + to.path);
    if (to.path == '/login') {
        //如果访问的是/login路径，就把原来存localStorage的用户信息移除掉
        localStorage.removeItem('user');
        localStorage.removeItem('U-TOKEN');
        localStorage.removeItem('R-TOKEN');
    }
    /*//从localStorage获取用户信息做判断
    let user = JSON.parse(localStorage.getItem('user'));
    console.log("user=" + user);
    //如果访问/login和/register路径以外的路径都需要user有值，才能继续访问，否则就跳转到登录页面进行登录
    if (!user &&(to.path != '/login' && to.path != '/register') ) {
        console.log("-------------------------1");
        //没有登录，则跳转到登录页面
        next({ path: '/login' })
    } else {
        console.log("-------------------------2");
        //已经登录,正常访问
        next()
    }*/

    next();
});
//router.afterEach(transition => {
//NProgress.done();
//});

new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App) // index.html id为app的div标签下面使用<App/>和template: '<App/>',一样的效果
}).$mount('#app') // 和el: '#app'效果一样都是挂载在index.html id为app的div标签上面

