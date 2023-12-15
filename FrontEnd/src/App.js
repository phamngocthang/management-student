import Container from "./components/Container";
import "./App.css";
import Header from "./components/Header";
import "./style/style.css";
import { ToastContainer } from "react-toastify";

function App() {
  return (
    <>
      <Header />
      <Container />

      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
    </>
  );
}

export default App;
