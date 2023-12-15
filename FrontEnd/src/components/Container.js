import TopNav from "./TopNav";
import UserContent from "./UserContent";

const Container = (props) => {
  return (
    <>
      <section class="dashboard">
        <TopNav />
        <UserContent />
      </section>
    </>
  );
};

export default Container;
