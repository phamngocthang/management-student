import { useEffect, useState } from "react";
import { Modal, Button } from "react-bootstrap";
import { updateUser } from "../services/UserService";
import { toast } from "react-toastify";

const ModalEditUser = (props) => {
  const { show, handleClose, dataUserEdit, handleUpdateUserFromModal } = props;
  const [name, setName] = useState("");
  useEffect(() => {
    setName(dataUserEdit.username);
  }, [dataUserEdit]);

  const handleEditUser = async () => {
    let res = await updateUser(dataUserEdit.id, name);
    if (res && res.data) {
      setName("");
      handleUpdateUserFromModal({
        id: dataUserEdit.id,
        username: name,
      });
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
          <Modal.Title>Edit New User</Modal.Title>
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
            </form>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleEditUser}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ModalEditUser;
