import { useEffect, useState } from "react";
import { fetchAllUser } from "../services/UserService";
import ReactPaginate from "react-paginate";
import ModalAddNew from "./ModalAddNew";
import ModalEditUser from "./ModalEditUser";
import ModalConfirm from "./ModalConfirm";
import _ from "lodash";

let searchByKeywordDto = {
  keyword: null,
  pageIndex: 0,
  pageSize: 7,
};

const UserContent = (props) => {
  const [listUsers, setListUsers] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [isShowModelAddNew, setIsShowModelAddNew] = useState(false);
  const [isShowModelEditNew, setIsShowModelEditNew] = useState(false);
  const [isShowModelDelete, setIsShowModelDelete] = useState(false);
  const [dataUserEdit, setDataUserEdit] = useState({});
  const [dataUserDelete, setDataUserDelete] = useState({});

  useEffect(() => {
    console.log("Effect");
    getUsers(searchByKeywordDto);
  }, [isShowModelAddNew]);

  const getUsers = async (searchByKeywordDto) => {
    console.log(searchByKeywordDto);
    let res = await fetchAllUser(searchByKeywordDto);
    if (res && res.data) {
      setListUsers(res.data);
      setTotalPages(res.totalRecords / searchByKeywordDto.pageSize);
    }
  };

  const handlePageClick = (event) => {
    searchByKeywordDto.pageIndex = +event.selected;

    getUsers(searchByKeywordDto);
  };

  const handleClose = () => {
    setIsShowModelAddNew(false);
    setIsShowModelEditNew(false);
    setIsShowModelDelete(false);
  };

  const handleOpenModelAddNew = () => {
    setIsShowModelAddNew(true);
  };

  const handleOpenModelEditNew = (user) => {
    setDataUserEdit(user);
    setIsShowModelEditNew(true);
  };

  const handleUpdateUserFromModal = (user) => {
    let cloneListUser = _.cloneDeep(listUsers);
    let index = listUsers.findIndex((item) => item.id === user.id);
    cloneListUser[index].username = user.username;
    setListUsers(cloneListUser);
  };

  const handleDeleteUserFromModal = (userId) => {
    let cloneListUser = _.cloneDeep(listUsers);
    cloneListUser = cloneListUser.filter((item) => item.id !== userId);
    setListUsers(cloneListUser);
  };

  const handleDeleteUser = (user) => {
    setDataUserDelete(user);
    setIsShowModelDelete(true);

    console.log(user);
  };

  // const handleUpdateTable = (user) => {
  //   setListUsers([user, ...listUsers]);
  // };

  return (
    <div className="dash-content">
      <div class="activity">
        <div class="title">
          <i class="uil uil-user-check"></i>
          <span class="text">USER</span>
          <button class="btn-new" onClick={handleOpenModelAddNew}>
            Add New
          </button>
        </div>
        <div class="activity-data">
          <div class="data id">
            <span class="data-title">ID</span>
            {listUsers &&
              listUsers.length > 0 &&
              listUsers.map((item, index) => {
                return <span class="data-list">{item.id}</span>;
              })}
          </div>
          <div class="data names">
            <span class="data-title">Name</span>
            {listUsers &&
              listUsers.length > 0 &&
              listUsers.map((item, index) => {
                return <span class="data-list">{item.username}</span>;
              })}
          </div>

          <div class="data action">
            <span class="data-title">Action</span>
            {listUsers &&
              listUsers.length > 0 &&
              listUsers.map((item, index) => {
                return (
                  <div className="data-action">
                    <button
                      class="data-list btn-action btn-edit"
                      onClick={() => handleOpenModelEditNew(item)}
                    >
                      UPDATE
                    </button>
                    <button
                      class="data-list btn-action btn-delete"
                      onClick={() => handleDeleteUser(item)}
                    >
                      DELETE
                    </button>
                  </div>
                );
              })}
          </div>
        </div>

        <ReactPaginate
          breakLabel="..."
          nextLabel="next >"
          onPageChange={handlePageClick}
          pageRangeDisplayed={5}
          pageCount={totalPages}
          previousLabel="< previous"
          pageClassName="page-item"
          pageLinkClassName="page-link"
          previousClassName="page-item"
          previousLinkClassName="page-link"
          nextClassName="page-item"
          nextLinkClassName="page-link"
          breakClassName="page-item"
          breakLinkClassName="page-link"
          containerClassName="pagination"
          activeClassName="active"
          renderOnZeroPageCount={null}
        />
      </div>

      <ModalAddNew
        show={isShowModelAddNew}
        handleClose={handleClose}
        // handleUpdateTable={handleUpdateTable}
      />

      <ModalEditUser
        show={isShowModelEditNew}
        handleClose={handleClose}
        dataUserEdit={dataUserEdit}
        handleUpdateUserFromModal={handleUpdateUserFromModal}
      />

      <ModalConfirm
        show={isShowModelDelete}
        handleClose={handleClose}
        dataUserDelete={dataUserDelete}
        handleDeleteUserFromModal={handleDeleteUserFromModal}
      />
    </div>
  );
};

export default UserContent;
