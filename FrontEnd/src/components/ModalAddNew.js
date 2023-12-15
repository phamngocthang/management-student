import { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import { createUser } from "../services/UserService";
import { toast } from "react-toastify";

const ModalAddNew = (props) => {
  const { show, handleClose } = props;
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const handleSaveUser = async () => {
    let res = await createUser(name, password);
    if (res && res.data) {
      setName("");
      setPassword("");
      handleClose();
      toast.success("Create User Successfully");
      console.log(res);
    } else {
      toast.error("Error!! Unable to create User");
    }
  };

  return (
    <>
      <Modal
        show={show}
        onHide={handleClose}
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Add New User</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="body-add-new">
            <form>
              <div class="form-group mb-3">
                <label>Username</label>
                <input
                  type="email"
                  class="form-control mt-2"
                  placeholder="Enter username"
                  value={name}
                  onChange={(event) => setName(event.target.value)}
                />
              </div>
              <div class="form-group mb-3">
                <label>Password</label>
                <input
                  type="password"
                  class="form-control mt-2"
                  placeholder="Password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                />
              </div>
            </form>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleSaveUser}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ModalAddNew;
