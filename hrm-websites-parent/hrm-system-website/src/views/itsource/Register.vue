<template>
  <div>
    <!--:model="tenant" 数据双向绑定-->
    <!--ref="tenantForm" id="tenantForm",给form去一个名字-->
    <!--:rules="formRules" 校验规则-->
    <el-form :model="employee" ref="tenantForm" :rules="formRules" label-position="left" label-width="100px" class="demo-ruleForm login-container">
      <h3 class="title">公司入驻</h3>
      <el-form-item prop="companyName"label="公司名称">
        <el-input type="text" v-model="employee.companyName" auto-complete="off" placeholder="请输入公司名称！"></el-input>
      </el-form-item>
      <el-form-item prop="companyNum" label="公司执照">
        <el-input type="text" v-model="employee.companyNum" auto-complete="off" placeholder="请输入执照号码！"></el-input>
      </el-form-item>
      <el-form-item prop="address" label="公司地址">
        <el-input type="text" style="width: 350px" v-model="employee.address" auto-complete="off" placeholder="请输入地址！"></el-input>
        <el-button size="small" type="primary" @click="selectAdrress">选择</el-button>
      </el-form-item>
      <el-form-item prop="logo" label="公司Logo">
        <!--<el-input type="text" v-model="employee.logo" auto-complete="off" placeholder="请输入logo！"></el-input>-->
        <el-upload
                class="upload-demo"
                action="http://localhost:1020/hrm/file/file/uploadFile"
                name="fileName"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                :file-list="fileList"
                list-type="picture">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item prop="meal" label="套餐选择">
        <el-select v-model="employee.mealId" placeholder="请选择">
          <el-option
                  v-for="item in meals"
                  :key="item.id"
                  :label="item.mealName"
                  :value="item.id">
            <span style="float: left">{{ item.mealName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.mealPrice }}</span>
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="meal" label="租户类型">
        <el-select v-model="employee.tenantTypeId" placeholder="请选择">
          <el-option
                  v-for="item in tenantTypes"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="username" label="公司账号">
        <el-input type="text" v-model="employee.username" auto-complete="off" placeholder="请输入账号！"></el-input>
      </el-form-item>
      <el-form-item prop="tel" label="手机号码">
        <el-input type="text" v-model="employee.tel" auto-complete="off" placeholder="请输入电话！"></el-input>
      </el-form-item>
      <el-form-item prop="email" label="电子邮件">
        <el-input type="text" v-model="employee.email" auto-complete="off" placeholder="请输入邮件！"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input type="password" v-model="employee.password" auto-complete="off" placeholder="请输入密码！"></el-input>
      </el-form-item>
      <el-form-item prop="comfirmPassword" label="确认密码">
        <el-input type="password" v-model="employee.comfirmPassword" auto-complete="off" placeholder="请输入确认密码！"></el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%;" @click.native.prevent="settledIn" >入驻</el-button>
      </el-form-item>
    </el-form>
    <el-dialog
            title="选择地址"
            :visible.sync="dialogVisable"
            width="30%">
      <baidu-map :center="{lng: 116.403765, lat: 39.914850}" :zoom="11">
        <bm-view class="map"></bm-view>
        <bm-control :offset="{width: '10px', height: '10px'}">
          <!--:sugStyle="{zIndex: 2100} 让搜索提示上浮-->
          <bm-auto-complete v-model="keyword" :sugStyle="{zIndex: 2100}">
            <div style="margin-bottom:10px">
              <input id="searchInput" type="text" placeholder="请输入关键字" class="searchinput"/>
              <el-button type="success" @click="selectAdrressConfirm">确定</el-button>
            </div>
          </bm-auto-complete>
        </bm-control>
        <bm-local-search :keyword="keyword" :auto-viewport="true" ></bm-local-search>
      </baidu-map>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisable=false">取 消</el-button>
        <el-button type="primary" @click="dialogVisable=false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        data() {
            var validatePass2 = (rule, value, callback) => {
                console.log(value); //确认密码
                if (value === '') {
                    callback(new Error('请再次输入密码'))
                } else if (value !== this.employee.password) {
                    callback(new Error('两次输入密码不一致!'))
                } else {
                    callback()
                }
            }
            return {
                meals:[],
                tenantTypes:[],
                keyword:'',
                dialogVisable:false,
                // fileList: [{"name":"xxx","http://localhost/"+this.employee.logo}],
                fileList: [],
                //employee:tenant 为了做数据表单校验不要嵌套对象
                employee: {
                    companyName: '源码时代',
                    companyNum: '110',
                    address: '天府新谷',
                    logo:'/xx.jpg',
                    tenantTypeId:'',

                    username:'itsource',
                    tel:'110',
                    email:'itsource@qq.com',
                    password:'123456',
                    comfirmPassword:'123456',

                    mealId:''
                },
                formRules: {
                    companyName: [
                        { required: true, message: '请输入公司名称!', trigger: 'blur' }
                    ],
                    // meal: [
                    //     { required: true, message: '请选择套餐!', trigger: 'blur' }
                    // ],
                    companyNum: [
                        { required: true, message: '请输入公司座机!', trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: '请输入公司地址!', trigger: 'blur' }
                    ],
                    logo: [
                        { required: true, message: '请输入公司logo!', trigger: 'blur' }
                    ],
                    username: [
                        { required: true, message: '请输入账号!', trigger: 'blur' }
                    ],
                    tel: [
                        { required: true, message: '请输入电话!', trigger: 'blur' }
                    ],
                    email: [
                        { type: 'email',required: true, message: '请输入邮箱!', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码!', trigger: 'blur' }
                    ],
                    comfirmPassword: [
                        {required: true,validator: validatePass2, trigger: 'blur' } //自定义校验规则
                    ]
                }
            };
        },
        mounted() {
            this.getMeals();//获取套餐
            this.getTenantTypes();//获取租户类型
        },
        methods: {
            getTenantTypes(){
                this.$http.get("/system/tenantType/list") //$.Post(.....)
                    .then(result=>{
                        this.tenantTypes = result.data.data;
                    });
            },
            getMeals(){
              this.$http.get("/auth/meal/list").then(res=>{
                  this.meals = res.data.data;
              });
            },
            selectAdrressConfirm(){
              //获取值搜索框值,设置给地址
                var searchInputV=document.getElementById("searchInput").value;
                this.employee.address = searchInputV;
                //关闭对话框
                this.dialogVisable = false;
            },
            selectAdrress(){
                this.dialogVisable = true;
            },
            handleSuccess(response, file, fileList){
                if(response.success){
                  this.$message({
                    message: '上传成功',
                    type: 'success'
                  });
                  this.employee.logo = response.data.filePath;
                }else{
                    this.$message({
                        message: '上传失败!',
                        type: 'error'
                    });
                }
            },
            handleRemove(file, fileList) {
                var path = file.response.data.ossKey;
                var param = {
                  "ossKey": path
                };
                this.$http.post("/file/file/deleteFile", param).then((res) => {
                    if(res.data.success){
                        this.$message({
                            message: res.data.data,
                            type: 'success'
                        });
                    }
                    else{
                        this.$message({
                            message: res.data.message,
                            type: 'error'
                        });
                    }

                });

            },
            handlePreview(file) {
                console.log(file);
            },
            //入住提交
            settledIn(){
                this.$refs.tenantForm.validate((valid) => {
                    //校验表单成功后才做一下操作
                    if (valid) {
                        this.$confirm('确认入驻吗？', '提示', {}).then(() => {
                            //拷贝后面对象的值到新对象,防止后面代码改动引起模型变化
                            var param = {
                                "tenant" : {
                                    "companyName":this.employee.companyName,
                                    "companyNum":this.employee.companyNum,
                                    "address":this.employee.address,
                                    "logo":this.employee.logo,
                                    "tenantTypeId":this.employee.tenantTypeId
                                },
                                "employee":{
                                    "tel" :this.employee.tel,
                                    "email" :this.employee.email
                                },
                                "username" :this.employee.username,
                                "password" :this.employee.password,
                                "mealId":this.employee.mealId
                            };

                            //判断是否有id有就是修改,否则就是添加
                            this.$http.post("/system/tenant/register", param).then((res) => {
                                if(res.data.success){
                                    this.$message({
                                        message: '操作成功!',
                                        type: 'success'
                                    });
                                    //重置表单
                                    this.$refs['tenantForm'].resetFields();
                                    //跳转登录页面
                                    this.$router.push({ path: '/login' });
                                }
                                else{
                                    this.$message({
                                        message: res.data.message,
                                        type: 'error'
                                    });
                                }

                            });
                        });
                    }
                })
            }
        }
    }

</script>

<style lang="scss" scoped>
  .login-container {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 500px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }

  .map {
    width: 100%;
    height: 300px;
  }
  .searchinput{
    width: 300px;
    box-sizing: border-box;
    padding: 9px;
    border: 1px solid #dddee1;
    line-height: 20px;
    font-size: 16px;
    height: 38px;
    color: #333;
    position: relative;
    border-radius: 4px;
  }
</style>