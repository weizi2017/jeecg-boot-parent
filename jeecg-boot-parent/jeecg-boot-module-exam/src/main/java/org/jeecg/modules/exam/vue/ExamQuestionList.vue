<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="题目类型">
              <j-dict-select-tag placeholder="请选择题目类型" v-model="queryParam.quesType" dictCode="exam_type"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="题目内容">
              <a-input placeholder="请输入题目内容" v-model="queryParam.quesContent"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="题目分值">
                <a-input placeholder="请输入题目分值" v-model="queryParam.quesMark"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('存储题目')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <examQuestion-modal ref="modalForm" @ok="modalFormOk"></examQuestion-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ExamQuestionModal from './modules/ExamQuestionModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "ExamQuestionList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      ExamQuestionModal
    },
    data () {
      return {
        description: '存储题目管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'题目类型',
            align:"center",
            dataIndex: 'quesType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['quesType'], text+"")
              }
            }
          },
          {
            title:'题目内容',
            align:"center",
            dataIndex: 'quesContent'
          },
          {
            title:'题目图片1',
            align:"center",
            dataIndex: 'quesImg1',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'题目图片2',
            align:"center",
            dataIndex: 'quesImg2',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'题目图片3',
            align:"center",
            dataIndex: 'quesImg3',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'题目图片4',
            align:"center",
            dataIndex: 'quesImg4',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'文字选项1',
            align:"center",
            dataIndex: 'quesCheck1'
          },
          {
            title:'文字选项2',
            align:"center",
            dataIndex: 'quesCheck2'
          },
          {
            title:'文字选项3',
            align:"center",
            dataIndex: 'quesCheck3'
          },
          {
            title:'文字选项4',
            align:"center",
            dataIndex: 'quesCheck4'
          },
          {
            title:'文字选项5',
            align:"center",
            dataIndex: 'quesCheck5'
          },
          {
            title:'图片选项1',
            align:"center",
            dataIndex: 'quesCheckImg1',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'图片选项2',
            align:"center",
            dataIndex: 'quesCheckImg2',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'图片选项3',
            align:"center",
            dataIndex: 'quesCheckImg3',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'图片选项4',
            align:"center",
            dataIndex: 'quesCheckImg4',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'图片选项5',
            align:"center",
            dataIndex: 'quesCheckImg5',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'答案',
            align:"center",
            dataIndex: 'quesAnswer'
          },
          {
            title:'答案解析',
            align:"center",
            dataIndex: 'quesKey'
          },
          {
            title:'题目分值',
            align:"center",
            dataIndex: 'quesMark'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/exam/examQuestion/list",
          delete: "/exam/examQuestion/delete",
          deleteBatch: "/exam/examQuestion/deleteBatch",
          exportXlsUrl: "/exam/examQuestion/exportXls",
          importExcelUrl: "exam/examQuestion/importExcel",
        },
        dictOptions:{
         quesType:[],
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('exam_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'quesType', res.result)
          }
        })
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>