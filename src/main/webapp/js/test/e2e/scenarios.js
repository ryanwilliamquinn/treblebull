'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('my app', function() {

  it('get the damn title right', function() {
    browser().navigateTo('/login');
    expect(browser().window().path()).toBe('/login');
    expect(element('title').text()).toBe("freaking darts");
  });

  it('should be able to login with test credentials', function() {
    browser().navigateTo('/login');
    input('username').enter('ryan');
    input('password').enter('t7c7f7a7m7');
    input('rememberMe').check();
    element(':submit').click();
    expect(element('title').text()).toBe("freaking darts");

  });


});

describe('target practice', function() {

  it('should show navigate the practice modes', function() {
    sleep(1);
    browser().navigateTo("/practice");
    sleep(1);
    expect(element(".button", "target buttons").count()).toBeGreaterThan(0);
  });

  it('should show the bullseye by default', function() {
    sleep(1);
    browser().navigateTo("/practice/target");
    expect(element("select:first option:selected").text()).toBe("bullseye");
    expect(element("select:nth-child(2) option:selected").text()).toBe("ten");
  });

  it('should show the buttons when you click on the start button', function() {
    sleep(1);
    browser().navigateTo("/practice/target");
    sleep(1);
    expect(element('.rounds').css('display')).toBe('none');
    element('#gameStart').click();
    expect(element('.rounds').css('display')).toBe('block');
    expect(element)
  });


});



