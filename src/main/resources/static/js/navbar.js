window.updateNavbar = function() {
    const token = localStorage.getItem('token');
    const guestNav = document.getElementById('guestNav');
    const userNav = document.getElementById('userNav');
    const myCourseLink = document.getElementById('myCourseLink');
    const myReviewLink = document.getElementById('myReviewLink');
    const applyAsTutorLink = document.getElementById('applyAsTutorLink');
    const seeReportLink = document.getElementById('seeReportLink');
    const userWelcome = document.getElementById('userWelcome');

    if (!guestNav || !userNav || !myCourseLink || !applyAsTutorLink || !seeReportLink || !userWelcome) {
        setTimeout(window.updateNavbar, 100);
        return;
    }

    if (token) {
        guestNav.style.display = 'none';
        userNav.style.display = 'flex';
        myCourseLink.style.display = 'block';
        myReviewLink.style.display = 'block';
        applyAsTutorLink.style.display = 'block';
        seeReportLink.style.display = 'block';
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            userWelcome.textContent = `👋 Hello, ${payload.fullName || payload.sub || 'User'}!`;

            if (payload.role === 'TUTOR') {
            applyAsTutorLink.style.display = 'none';
            seeReportLink.style.display = 'none';

            myCourseLink.href = '/courses/tutor/courseslist';
        }
        } catch (e) {
            userWelcome.textContent = '👋 Hello, User!';
        }
    } else {
        guestNav.style.display = 'flex';
        userNav.style.display = 'none';
        myCourseLink.style.display = 'none';
        myReviewLink.style.display = 'none';
        applyAsTutorLink.style.display = 'none';
        seeReportLink.style.display = 'none';
    }
};

window.logout = function() {
    localStorage.removeItem('token');
    window.updateNavbar();
    window.location.href = '/courses';
};

function waitForNavbar() {
    const observer = new MutationObserver((mutations) => {
        if (document.getElementById('guestNav')) {
            observer.disconnect();
            window.updateNavbar();
        }
    });
    observer.observe(document.body, { childList: true, subtree: true });
    setTimeout(() => window.updateNavbar(), 50);
}

waitForNavbar();
