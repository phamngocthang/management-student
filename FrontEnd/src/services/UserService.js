// import axios from "axios";
import axios from "./AxiosCustom";

const fetchAllUser = (searchByKeywordDto) => {
  return axios.post("/users/search-by-keyword", searchByKeywordDto);
};

const createUser = (name, password) => {
  return axios.post("/users/add", { username: name, password });
};

const updateUser = (id, name) => {
  return axios.put("/users/update/" + id, { username: name });
};

const deleteUser = (id) => {
  return axios.delete("/users/delete/" + id);
};
export { fetchAllUser, createUser, updateUser, deleteUser };
