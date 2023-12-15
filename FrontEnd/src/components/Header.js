const Header = (props) => {
  return (
    <>
      <nav>
        <div class="logo-name">
          <div class="logo-image">
            <img src="images/logo.png" alt="" />
          </div>

          <span class="logo_name">Pham Ngoc Thang</span>
        </div>

        <div class="menu-items">
          <ul class="nav-links">
            <li>
              <a href="#">
                <i class="uil uil-estate"></i>
                <span class="link-name">Dahsboard</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="uil uil-user-square"></i>
                <span class="link-name">User</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="uil uil-book-reader"></i>
                <span class="link-name">Student</span>
              </a>
            </li>
          </ul>

          <ul class="logout-mode">
            <li>
              <a href="#">
                <i class="uil uil-signout"></i>
                <span class="link-name">Logout</span>
              </a>
            </li>

            <li class="mode">
              <a href="#">
                <i class="uil uil-moon"></i>
                <span class="link-name">Dark Mode</span>
              </a>

              <div class="mode-toggle">
                <span class="switch"></span>
              </div>
            </li>
          </ul>
        </div>
      </nav>
    </>
  );
};

export default Header;
