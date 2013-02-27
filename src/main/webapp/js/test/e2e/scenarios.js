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
    expect(element('.rounds').css('display')).toBe('none');
    element('#gameStart').click();
    expect(element('.rounds').css('display')).toBe('block');
    expect(element('#gameMode').text()).toBe('target : bullseye');
  });

  it('should record some scores and stuff', function() {
     sleep(1);
     browser().navigateTo("/practice/target");
     element('#gameStart').click();
     expect(element('#gameAverage').css("display")).toBe("none");
     element('.sTarget:first').click();
     element('.sTarget:first').click();
     element('.sTarget:first').click();
     expect(element('#gameAverage').text()).toContain("Round average: 3");
  });


  it('should have double and triple buttons that work', function() {
       sleep(1);
       browser().navigateTo("/practice/target");
       element('#gameStart').click();
       expect(element('#gameAverage').css("display")).toBe("none");

       // test the scoring mechanism
       element('.sTarget:first').click(); // round score is 1
       element('.dtTarget:first').click(); // click the double
       element('.sTarget:first').click(); // round score is 3
       element('.dtTarget:nth-child(2)').click(); // click the triple
       element('.sTarget:first').click(); // round score is 6
       expect(element('#gameAverage').text()).toContain("Round average: 6");

       // test the highlighting
       expect(element('.dtTarget:first').text()).toBe("D");
       element('.dtTarget:first').click();
       expect(element('.dtTarget:first').css('background-color')).toBe('rgb(0, 238, 0)');
       element('.dtTarget:first').click();
       expect(element('.dtTarget:first').css('background-color')).toBe('rgba(0, 0, 0, 0)');
    });

  it('should have a working cancel button', function() {
    sleep(1);
    browser().navigateTo("/practice/target");
    element('#gameStart').click();
    expect(element('#gameAverage').css("display")).toBe("none");

    // test the scoring mechanism
    element('.sTarget:first').click(); // round score is 1
    element('.dtTarget:first').click(); // click the double
    element('.sTarget:first').click(); // round score is 3
    element('.dtTarget:nth-child(2)').click(); // click the triple
    element('.sTarget:first').click(); // round score is 6
    expect(element('#gameAverage').css("display")).toBe("block");
    expect(element('#gameAverage').text()).toContain("Round average: 6");

    element('#cancelGame').click();
    expect(element('#gameAverage').css("display")).toBe("none");

  })

  it('should have a functional editing', function() {
    sleep(1);
    browser().navigateTo("/practice/target");
    element('#gameStart').click();
    expect(element('#gameAverage').css("display")).toBe("none");

    // test the scoring mechanism
    element('.sTarget:first').click(); // round score is 1
    element('.dtTarget:first').click(); // click the double
    element('.sTarget:first').click(); // round score is 3
    element('.dtTarget:nth-child(2)').click(); // click the triple
    element('.sTarget:first').click(); // round score is 6
    expect(element('#gameAverage').css("display")).toBe("block");
    expect(element('#gameAverage').text()).toContain("Round average: 6");

    element('#roundResults span[name=roundResult]:first').click();
  })

});



