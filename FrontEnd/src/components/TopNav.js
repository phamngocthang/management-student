const TopNav = (props) => {
  return (
    <>
      <div class="top">
        <i class="uil uil-bars sidebar-toggle"></i>

        <div class="search-box">
          <i class="uil uil-search"></i>
          <input type="text" placeholder="Search here..." />
        </div>
      </div>
    </>
  );
};

export default TopNav;
