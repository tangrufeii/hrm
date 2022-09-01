<template id="courseType">
    <div>
        <el-row style="height: 100%;border: 1px solid #DCDFE6;margin-top: 10px">
            <el-col :span="6" style="border-right: 1px solid #DCDFE6; min-height:500px;">
                <div class="grid-content bg-purple">
                    <el-tree :data="courseTypes" :props="defaultProps"  @node-click="handleNodeClick">
                    </el-tree>
                </div>
            </el-col>
            <el-col :span="17" style="margin-left: 10px;padding-top: 10px">
                <!--工具条-->
                <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
                    <el-form :inline="true" :model="filters">
                        <el-form-item>
                            <el-input v-model="filters.keyword" placeholder="关键字"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" v-on:click="getList">查询</el-button>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="handleAdd">新增</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>

                <!--列表-->
                <el-table :data="datas" highlight-current-row  @selection-change="handleSelectionChange" style="width: 100%;">
                    <el-table-column type="selection" >
                    </el-table-column>
                    <el-table-column prop="name" label="标题" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="logo" label="LOGO" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="description" label="描述" width="120" sortable>
                    </el-table-column>
                    <el-table-column prop="totalCount" label="课程数量" width="120" sortable>
                    </el-table-column>

                    <el-table-column label="操作" width="150">
                        <template scope="scope">
                            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!--工具条-->
                <el-col :span="24" class="toolbar">
                    <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
                </el-col>

            </el-col>
        </el-row>

        <!--新增界面-->
        <el-dialog title="新增" :visible.sync="addFormVisible"  :close-on-click-modal="false">
            <el-form :model="addForm" label-width="80px"  ref="addForm">
                <el-form-item label="分类标题" prop="name">
                    <el-input v-model="addForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="LOGO" prop="logo">
                    <el-input v-model="addForm.logo" auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="排序" prop="sortIndex">
                    <el-input v-model="addForm.sortIndex" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="addForm.description" auto-complete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" >提交</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<style>
    .el-row {
        margin-bottom: 20px;
        height: 100%;
    }
    :last-child {
        margin-bottom: 0;
    }
    #courseType el-col {
        border: 1px solid red;
        border-radius: 4px;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
</style>

<script>
    export default {
        data() {
            return {
                addForm:{
                    name:"",
                    logo:"",
                    sortIndex:"",
                    description:"",
                    pid:""
                },
                addFormVisible:false,
                sels:[],
                filters:{
                    keyword:""
                },
                datas:[],
                courseTypes:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        methods:{
            //新增
            handleAdd(){
              this.addFormVisible = true;
              this.addForm = {
                  name:"",
                  logo:"",
                  sortIndex:"",
                  description:"",
                  pid: this.addForm.pid,
              };
            },
            addSubmit(){
                //新增课程类型时，必须要设置父节点ID
                if(this.addForm.pid == null || this.addForm.pid == "" || this.addForm.pid == undefined){
                    this.$message({
                        message: "请选选择父节点！",
                        type: 'error'
                    });
                    return;
                }
                //提交
                this.$http.post("/course/courseType/save", this.addForm).then(res=>{
                    var ajaxResult = res.data;
                    if(ajaxResult.success){
                        this.addFormVisible = false;
                        this.$message({
                            message: ajaxResult.message,
                            type: 'success'
                        });
                        //刷新树型
                        this.getTreeData();
                        //刷新右侧表格
                        this.refreshDatas();
                    }else{
                        this.$message({
                            message: ajaxResult.message,
                            type: 'error'
                        });
                    }
                });
            },
            handleCurrentChange(){
            },
            //批量删除
            batchRemove(){
                this.$confirm('确定删除这些数据吗？', '请确认', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let param = {
                        "ids": this.sels
                    };
                    this.$http.post("/course/courseType/deleteBatch", param).then(result=>{
                        if(result.data.success){
                            //刷新树型
                            this.getTreeData();
                            //刷新右侧表格
                            this.refreshDatas();
                            this.$message({
                                type: 'success',
                                message: result.data.message
                            });
                        }else{
                            this.$message({
                                type: 'error',
                                message: result.data.message
                            });
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                });
            },
            //编辑
            handleEdit(index, row){
                this.addFormVisible = true;
                this.addForm = row;
            },
            //删除记录
            handleDel(index, row){
                // console.log(row.id);
                this.$confirm('确定删除此条数据吗？', '请确认', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.delete("/course/courseType/" + row.id).then(result=>{
                        if(result.data.success){
                            //刷新树型
                            this.getTreeData();
                            //刷新右侧表格
                            this.refreshDatas();
                            this.$message({
                                type: 'success',
                                message: result.data.message
                            });
                            this.getCourses();
                        }else{
                            this.$message({
                                type: 'error',
                                message: result.data.message
                            });
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                });
            },
            //表格多条数据选中事件
            handleSelectionChange(val) {
                /**
                 * val中存放的是所有已选择的行数据集合
                 */
                this.sels = [];
                if(val != null && val.length > 0){
                    for(var i=0; i<val.length; i++){
                        //往sels数组中存值
                        this.sels.push(val[i].id);
                    }
                }
            },
            //查询右侧表格数据
            getList(){
                let param = {
                    "keyword": this.filters.keyword,
                    "pid": this.addForm.pid
                };
                this.listLoading = true; //显示加载圈
                //分页查询
                this.$http.post("/course/courseType/pagelist", param).then(result=>{
                    this.datas = result.data.data.rows;
                    this.listLoading = false;  //关闭加载圈
                });
            },

            //树型节点点击事件
            handleNodeClick(row){
                this.datas = row.children;
                this.addForm.pid = row.id;
            },
            //刷新右侧表格数据
            refreshDatas(){
                this.$http.get("/course/courseType/getCourseTypeByPid/" + this.addForm.pid).then(result=>{
                    if(result.data.success){
                        this.datas = result.data.data;
                        this.getCourses();
                    }else{
                        this.$message({
                            type: 'error',
                            message: result.data.message
                        });
                    }
                });
            },
            getTreeData(){
                // 发送一个异步请求: get请求 /product/courseType/treeData
                this.$http.get("/course/courseType/treeData").then(res=>{
                    this.courseTypes = res.data.data;
                });
            }
        },
        mounted(){
            //对courseTypes数据赋值
           this.getTreeData();
        }
    };
</script>