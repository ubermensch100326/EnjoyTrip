<script setup></script>

<template>
    <div class="container">
        <footer
            class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top"
        >
            <div class="col-4">
                <p id="copyright" class="mb-0 text-muted d-inline">&copy;</p>
                <p class="mb-0 text-muted d-inline">ssafy</p>
            </div>

            <p
                class="col-md-4 d-flex align-items-center justify-content-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none"
            >
                <img src="@/assets/enjoytrip_logo.png" alt="" width="40" />
            </p>
            <ul class="nav col-md-4 justify-content-end">
                <li class="nav-item">
                    <a href="#" class="nav-link px-2 text-muted">이용약관</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link px-2 text-muted">개인정보처리방침</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link px-2 text-muted">공지사항</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link px-2 text-muted">문의하기</a>
                </li>
            </ul>
        </footer>
    </div>
</template>

<style scoped>
#copyright {
    font-family: "Consolas";
}
</style>
