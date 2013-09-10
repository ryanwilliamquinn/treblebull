module.exports = function(config){
  config.set({
basePath : '../',

files : [
  'angular/angular.js',
  'angular/angular-*.js',
  'test/lib/angular/angular-mocks.js',
  'dartsApp.js',
  'targetController.js',
  'threeOhOneController.js',
  'filters.js',
  'services.js',
  'utils.js',
  'test/*.js',
  'test/unit/**/*.js'
],

autoWatch : true,

frameworks: ['jasmine'],

browsers : ['Chrome'],

plugins : [
  'karma-junit-reporter',
  'karma-chrome-launcher',
  'karma-firefox-launcher',
  'karma-jasmine'       
],

  junitReporter : {
  outputFile: 'test_out/unit.xml',
  suite: 'unit'
}

})}
