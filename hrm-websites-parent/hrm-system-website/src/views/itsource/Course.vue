<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :model="filters" :inline="true">
				<el-form-item>
					<el-input v-model="filters.keyword" placeholder="关键字" ></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="warning" v-on:click="getCourses">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="addHandler" >新增</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="success" @click="onLineCourse" >上线</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="danger" @click="offLineCourse" >下线</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="info" @click="uploadResources" >上传资源</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表v-loading="listLoading"-->
		<el-table @row-click="rowClick" :cell-style="cellStyle" :data="courses" v-loading="listLoading" @selection-change="handleSelectionChange"
				  highlight-current-row  style="width: 100%;">
			<!--多选框-->
			<el-table-column type="selection" width="55">
			</el-table-column>
			<!--索引值,为什么不用id,id不序号-->
			<el-table-column type="index" width="60">
			</el-table-column>
			<!--其他都设置值,只有一个不设置值就自动适应了-->
			<el-table-column prop="course.name" label="课程名称">
			</el-table-column>
			<!--<el-table-column prop="courseType.name" label="类型">-->
			<!--</el-table-column>-->
			<el-table-column prop="course.gradeName" label="等级">
			</el-table-column>
			<el-table-column prop="course.status" label="状态" :formatter="statusFormatter">
			</el-table-column>
			<el-table-column prop="course.forUser" label="适用人群">
			</el-table-column>
			<!--<el-table-column prop="tenantName" label="所属机构">-->
			<!--</el-table-column>-->
			<el-table-column prop="course.userName" label="创建用户">
			</el-table-column>
			<el-table-column prop="course.startTime" label="开课时间">
			</el-table-column>
			<el-table-column prop="course.endTime" label="结课时间">
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template scope="scope">
					<el-button size="small"  @click="edit(scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="del(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" :disabled="this.sels.length===0" @click="delBatch">批量删除</el-button>
			<el-pagination
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="page"
					:page-sizes="[5, 10, 50, 100, 500, 1000]"
					:page-size="rows"
					layout="total, sizes, prev, pager, next, jumper"
					:total="totalSize"
					style="float:right;">
			</el-pagination>
		</el-col>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible"  :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px"  ref="addForm">
				<el-form-item label="课程名称" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="适用人群" prop="forUser">
					<el-input v-model="addForm.forUser" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="课程等级" prop="gradeId">
					<el-radio-group v-model="addForm.gradeId">
						<el-radio v-for="grade in grades" :label="grade.dicKey">{{grade.dicValue}}</el-radio>
					</el-radio-group>
				</el-form-item>

				<el-form-item label="开课日期" >
					<el-date-picker
							v-model="addForm.startTime"
							type="date"
							value-format="yyyy-MM-dd"
							size="small"
							placeholder="课程开始日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="节课日期" >
					<el-date-picker
							v-model="addForm.endTime"
							type="date"
							value-format="yyyy-MM-dd"
							size="small"
							placeholder="课程结束日期">
					</el-date-picker>
				</el-form-item>


				<el-form-item label="收费规则" prop="gradeId">
					<el-radio-group v-model="addForm.chargeId">
						<el-radio @change="changeCharge" v-for="charge in charges" :label="charge.id">{{charge.name}}</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="课程价格" prop="price">
					<el-input :disabled="priceDisabled" v-model="addForm.price" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="课程原价">
					<el-input :disabled="priceDisabled" v-model="addForm.priceOld" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="咨询QQ" prop="qq">
					<el-input v-model="addForm.qq" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="结束时间" >
					<el-date-picker
							v-model="addForm.expires"
							type="date"
							value-format="yyyy-MM-dd"
							size="small"
							placeholder="营销结束时间">
					</el-date-picker>
				</el-form-item>


				<el-form-item label="课程类型" prop="coursetTypId">
					<el-cascader
							:props="courseTypeProps"
							v-model="addForm.courseTypeId"
							placeholder="试试搜索：Java"
							:options="courseTypes"
							expand-trigger="hover"
							:show-all-levels="false"
							filterable
							@change="handleChange"
							change-on-select
					></el-cascader>
				</el-form-item>

				<el-form-item prop="logo" label="课程封面">
					<!--<el-input type="text" v-model="employee.logo" auto-complete="off" placeholder="请输入logo！"></el-input>-->
					<el-upload
							class="upload-demo"
							action="http://localhost:1020/hrm/file/fastdfs/upload"
							:on-preview="handlePreview"
							:on-remove="handleRemove"
							:on-success="handleSuccess"
							:file-list="fileList"
							list-type="picture">
						<el-button size="small" type="primary">点击上传</el-button>
						<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
					</el-upload>
				</el-form-item>


				<el-form-item label="课程简介" prop="description">
					<el-input
							type="textarea"
							:rows="2"
							placeholder="请输入内容"
							v-model="addForm.description">
					</el-input>
				</el-form-item>

				<el-form-item label="课程详情" prop="intro">
					<div class="edit_container">
						<quill-editor
								v-model="addForm.intro"
								ref="myQuillEditor"
								class="editer"
								:options="editorOption"
								@ready="onEditorReady($event)">
						</quill-editor>
					</div>
				</el-form-item>


			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" >提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
    import { quillEditor } from "vue-quill-editor"; //调用编辑器
    import "quill/dist/quill.core.css"
	import "quill/dist/quill.snow.css"
	import "quill/dist/quill.bubble.css"

	export default {
        computed: {
			editor() {
                return this.$refs.myQuillEditor.quill
            }
		},
        components: {//使用编辑器
            quillEditor
        },
		data() {
			return {
                row:"",
				sels: [], //存储多选记录
                courseTypeProps:{
                    value:"id",
                    label:"name"
				},
                priceDisabled:true,
                editorOption: {},//富文本编辑框配置
			    grades:[],
				fileList:[],
                charges:[
					{"id":1 , "name":"免费"},
					{"id":2 , "name":"收费"}
				],
				courseTypes:[],
                addFormVisible:false,
				//images:[xxx.jgp,xxxx,jpg,xxxx.jpg],
				addForm:{
                    id:'',
                    startTime:'',
                    endTime:'',
                    expires:'',
                    name:'',
                    forUser:'',
                    gradeId:'',
                    courseTypeId:'',
                    description:'',
                    intro:'',
                    chargeId:'',
                    price:'',
                    priceOld:'',
                    qq:'',
                    pic:''
				},
                listLoading:false,
				//查询对象
				filters:{
					keyword:''
				},
				totalSize: 0,//总记录条数
				page:1,//当前页,要传递到后台的
				rows:10, //每页显示多少条
			    courses:[], //当前页数据
			}
		},
		methods: {
			//根据状态不同显示不同颜色字体
			cellStyle(row, column, rowIndex, columnIndex){
				if(row.row.course.status === 0 && row.column.label==="状态"){
					return 'color:red'
				}
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
						this.sels.push(val[i].course.id);
					}
				}
			},
            handleSuccess(response, file, fileList){
                if(response.success){
                    this.addForm.pic = response.resultObj;
                }else{
                    this.$message({
                        message: '上传失败!',
                        type: 'error'
                    });
                }
            },
			handleRemove(){
            },
			handlePreview(){
            },
            addSubmit(){
            	// console.log("this.addForm.courseTypeId=" + this.addForm.courseTypeId);
            	//获取用户选择的课程类型ID
                this.addForm.courseTypeId = this.addForm.courseTypeId[this.addForm.courseTypeId.length - 1];

                //获取用户选择的课程等级中文名称，数据库需要存冗余字段
				var gradeName;
				for(var i = 0 ; i < this.grades.length ; i++){
                    var grade = this.grades[i];
                    if(grade.dicKey === this.addForm.gradeId){
                        gradeName = grade.dicValue;
                        break;
					}
				}
                var param = {
                    course:{
                        id:this.addForm.id,
                        courseTypeId:this.addForm.courseTypeId,
                        name:this.addForm.name,
                        forUser:this.addForm.forUser,
                        gradeId:this.addForm.gradeId,
                        gradeName: gradeName,
                        // pic:this.addForm.pic,
                        pic: "https://hrm0620.oss-cn-chengdu.aliyuncs.com/111.jpg",
                        startTime:this.addForm.startTime,
                        endTime:this.addForm.endTime
					},
                	courseDetail:{
                        description:this.addForm.description,
                        intro:this.addForm.intro
					},
                	courseMarket:{
                        charge:this.addForm.chargeId,
                        qq:this.addForm.qq,
                        price:this.addForm.price,
                        priceOld:this.addForm.priceOld,
                        expires:this.addForm.expires
					}
				};

                this.$http.post("/course/course/save", param).then(res=>{
                    var ajaxResult = res.data;
					if(ajaxResult.success){
                        this.$message({
                            message: '保存成功!',
                            type: 'success'
                        });
                        this.addFormVisible = false;
                        this.getCourses();
                    }else{
                        this.$message({
                            message: '上传失败!',
                            type: 'error'
                        });
                    }
				});
			},
			//获取课程等级
			getGrades(){
              this.$http.get("/system/systemdictionarydetail/listBySn/dj").then(result=>{
                  this.grades = result.data.data;
              });
			},
			//获取课程类型
            getCourseTypes(){
              this.$http.get("/course/courseType/treeData").then(result=>{
                  this.courseTypes = result.data.data;
              });
			},
			//编辑操作
			edit(row){
				this.addFormVisible = true;//显示编辑框
				this.addForm = row.course;

				this.addForm.chargeId = row.courseMarket.charge;
				this.addForm.expires = row.courseMarket.expires;
				this.addForm.qq = row.courseMarket.qq;
				this.addForm.price = row.courseMarket.price;
				this.addForm.priceOld = row.courseMarket.priceOld;

				this.addForm.description = row.courseDetail.description;
				this.addForm.intro = row.courseDetail.intro;
			},
			//批量删除
			delBatch(){
				this.$confirm('确定删除这些数据吗？', '请确认', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let param = {
						"ids": this.sels
					};
					this.$http.post("/course/course/deleteBatch", param).then(result=>{
						if(result.data.success){
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
			//删除单条数据
			del(row){
				this.$confirm('确定删除此条数据吗？', '请确认', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http.delete("/course/course/" + row.course.id).then(result=>{
						this.courseTypes = result.data.data;
						if(result.data.success){
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
            changeCharge(){
            	let chargeId = this.addForm.chargeId;
                if(chargeId === 1){
					//免费
					this.addForm.price = "";
					this.addForm.priceOld = "";
                    this.priceDisabled = true;
				}else{
                	//收费
                    this.priceDisabled = false;
				}
			},
            onEditorReady(editor) {
                //当富文本编辑框初始化好执行
            },
            addHandler(){
				this.addForm = {};
				this.addFormVisible = true;
			},
			//选择第几页时触发
            handleCurrentChange(curentPage){
                this.page = curentPage;
                this.getCourses();
			},
			//选择每页显示记录条数
			handleSizeChange(curentPage){
				this.rows = curentPage
                this.getCourses();
			},
			handleChange(value) {
				console.log(this.addForm.courseTypeId);
			},
            getCourses(){
                //发送Ajax请求后台获取数据  axios
				//添加分页条件及高级查询条件
				let para = {
				    "page": this.page,
				    "rows": this.rows,
					"keyword":this.filters.keyword
				};
				this.listLoading = true; //显示加载圈
				//分页查询
                this.$http.post("/course/course/pagelist",para) //$.Post(.....)
                    .then(result=>{
                        this.totalSize = result.data.data.total;
                        this.courses = result.data.data.rows;
                        this.listLoading = false;  //关闭加载圈
                    });
			},
			//上线
            onLineCourse(){
                //获取选中的行
				if(this.sels.length  === 0){
            this.$message({ message: '老铁，你不选中数据，臣妾上不了啊....',type: 'error'});
				    return;
				}

        console.log("11111111111111111111");
        console.log(this.sels);

				let para = {
					"ids": this.sels
				};
				this.$http.post("/course/course/onLineCourse", para).then(res=>{
				    var ajaxResult = res.data;
				    if(ajaxResult.success){
                        this.$message({ message: ajaxResult.data, type: 'success'});
                        this.getCourses();
					}else{
                        this.$message({ message: ajaxResult.message,type: 'error'});
					}
				});
			},
            offLineCourse(){
                //获取选中的行
				if(this.sels.length  === 0){
					this.$message({ message: '老铁，你不选中数据，臣妾下线不了啊....',type: 'error'});
					return;
				}
				let para = {
					"ids": this.sels
				};
                this.$http.post("/course/course/offLineCourse", para).then(res=>{
                    var ajaxResult = res.data;
                    if(ajaxResult.success){
                        this.$message({ message: ajaxResult.message, type: 'success'});
                        this.getCourses();
                    }else{
                        this.$message({ message: ajaxResult.message,type: 'error'});
                    }
                });
			},
			//单击行事件
            rowClick(row){
				this.row = row;
			},
			uploadResources(){
			},
            //性别显示转换
            statusFormatter: function (row, column) {
                return row.course.status == 1 ? '已上线' : '未上线';
            },
		},
		mounted() {
		    this.getCourses();
		    this.getGrades();
		    this.getCourseTypes();
		}
	}

</script>

<style scoped>

</style>