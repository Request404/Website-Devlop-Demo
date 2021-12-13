<template>
  <div id="data_wrap">
    <header id="wrap_header">
      <div id="search_wrap">
        <Input v-model="keyword" type="number" size="large" search enter-button placeholder="请输入要查询的条件信息" @keyup.enter.native="searchForm" @click.native="searchForm"/>
      </div>
      <div id="add_drawer">
        <add/>
      </div>
    </header>
    <div id="add_table">
      <Table no-data-text="暂无数据" height="535" max-height="540" border :columns="columns" :data="data">
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row }" slot="productCoverUrl">
          {{ row.productCoverUrl ? row.productCoverUrl : '暂无数据' }}
        </template>
        <template slot-scope="{ row }" slot="productType">
          {{ row.productType === 1 ? '书籍' : row.productType === 2 ? '电影' : '音乐' }}
        </template>
        <template slot-scope="{ row, index }" slot="state">
          <Button :type="row.state === 1?'warning':'success'" size="small" style="margin-right: 5px"
                  @click="getOff(index)">{{ row.state === 1 ? '下架' : '上架' }}
          </Button>
        </template>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看
            <Icon type="md-clipboard"/>
          </Button>
          <span>
            <update :optionData="row"/>
          </span>
          <Button type="error" size="small" @click="remove(index)">删除
            <Icon type="md-trash"/>
          </Button>
        </template>
      </Table>
    </div>
    <Page style="margin-top: 1rem" :current="page" :total="total" @on-change="onPage"/>
  </div>
</template>

<script>
import Add from "./add";
import Update from "./update";

export default {
  name: "index",
  components: {Add, Update},
  data() {
    return {
      keyword: '',
      columns: [
        {
          title: 'ID',
          key: 'id',
          width: 115,
          sortable: true
        },
        {
          title: '产品编号',
          key: 'productId',
          width: 140
        },
        {
          title: '产品类型',
          slot: 'productType',
          width: 95
        },
        {
          title: '宣传封面地址',
          slot: 'productCoverUrl',
          width: 460
        },
        {
          title: '状态',
          slot: 'state',
          width: 90,
          filters: [
            {
              label: '已下架',
              value: 1
            },
            {
              label: '已上架',
              value: 2
            }
          ],
          filterMultiple: false,
          filterMethod(value, row) {
            if (value === 1) {
              return row.state === 0;
            } else if (value === 2) {
              return row.state === 1;
            }
          }
        },
        {
          title: '操作时间',
          key: 'operationTime',
          width: 180,
          sortable: true
        }, {
          title: '操作人',
          key: 'operationUser',
          width: 120
        },
        {
          title: '操作',
          slot: 'action',
          width: 235,
          align: 'center'
        }
      ],
      data: [],
      total: null,
      page: 1
    }
  },
  watch: {
    keyword: function (newVal) {
      if (newVal === ''){
        this.api.dRecordGetRecommendRecord(0, 10, 0).then(rs => {
          this.data = rs.data.data.records
          this.total = rs.data.data.total + 1
        }).catch(err => {
          console.log(err);
        })
      }
    }
  },
  methods: {
    searchForm() {
      this.api.dRecordGetRecommendRecord(0, 10, this.keyword === '' ? 0 : this.keyword).then(rs => {
        this.data = rs.data.data.records
        this.total = rs.data.data.total + 1
      }).catch(err => {
        console.log(err);
      })
    },
    show(index) {
      this.$Modal.info({
        title: '推荐记录信息',
        content: `<b>ID：</b>${this.data[index].id}<br>
          <b>文化产品编号：</b>${this.data[index].productId}<br>
          <b>文化产品类型：</b>${this.data[index].productType === 1 ? '书籍' : this.data[index].productType === 2 ? '电影' : '音乐'}<br>
          <b>宣传封面地址：</b>${this.data[index].productCoverUrl ? this.data[index].productCoverUrl : '无'}<br>
          <b>操作时间：</b>${this.data[index].operationTime}<br>
          <b>操作人：</b>${this.data[index].operationUser}<br>`
      })
    },
    remove(index) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.dRecordRemove(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            location.reload();
          } else {
            this.$message.error('删除失败');
          }
        }).catch(err => {
          console.log(err);
          this.$message.error('删除失败');
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    getOff(index) {
      this.$confirm(this.data[index].state === 1 ? '要将此列下架?' : '要将此列重新上架?', '提示', {
        confirmButtonText: this.data[index].state === 1 ? '下架?' : '上架',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.api.dRecordGetOff(this.data[index].id).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            location.reload();
          } else {
            this.$message.error('操作失败');
          }
        }).catch(err => {
          console.log(err);
          this.$message.error('操作失败');
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.data[index].state === 1 ? '取消下架?' : '取消上架'
        });
      });
    },
    onPage(page) {
      sessionStorage.setItem('dRecordsPage', page);
      location.reload();
    }
  },
  created() {
    if (sessionStorage.getItem('dRecordsPage')) {
      this.page = parseInt(sessionStorage.getItem('dRecordsPage'))
    }
    this.api.dRecordGetRecommendRecord(this.page, 10, 0).then(rs => {
      this.data = rs.data.data.records
      this.total = rs.data.data.total + 1
    }).catch(err => {
      console.log(err);
    })
  }
}
</script>

<style scoped lang="scss">
#data_wrap {
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  align-items: center;

  #wrap_header {
    width: 98%;
    height: 15%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    #search_wrap {
      width: 45%;
      display: flex;
      align-items: center;

    }

    #add_drawer {
      width: auto;
      height: auto;
    }
  }

  #add_table {
    width: 98%;
  }
}

.demo-drawer-footer {
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  background: #fff;
}
</style>
