function loading(outputDiv) {
  // $(".output").html('<pre><span class="loading">'+('waiting')+'</span></pre>');
  outputDiv.html('<pre><span class="loading">' + ('waiting') + '</span></pre>');
}

function runFunc(codeStr, outputDiv) {
  $.ajax("/run?code=" + escape(codeStr), {
    type: "GET",
    dataType: "json",
    success: function(data) {
      if (!data) {
        return;
      }
      if (data.Errors && data.Errors.length > 0) {
        setOutput(outputDiv, null, data.Errors);
        return;
      }
      setOutput(outputDiv, data.Events, false);
    },
    error: function() {
      outputDiv.addClass("error").text(
        "Error communicating with remote server.");
    }
  });
}

function setOutput(output, events, error) {
  output.empty();
  if (events) {

    var msg = ""
    for (var i = 0; i < events.length; i++) {
      msg += events[i] + "\n"
    }
    output.text(msg);

    var exit = $('<span class="exit"/>');
    exit.text("\nProgram exited.");
    exit.appendTo(output);
  }
  // Display errors.
  if (error) {
    var errorText = ""
    for (var i = 0; i < error.length; i++) {
      errorText += error[i] + "\n"
    }
    output.addClass("error").text(errorText);
    return;
  }
}

var editors = {}

$(".run").click(function() {
  var editorText = $(this).parent().parent().parent().find(".editor");
  var outputDiv = $(this).parent().parent().parent().find(".output");
  loading(outputDiv);
  var k = -1
  var editorTexts = $(".editor")
  for (var i = 0; i < editorTexts.length; i++) {
    if (getElementPath(editorText) == getElementPath(editorTexts[i])) k = i
  }
  var editor = editors[k]
  runFunc(editor.getValue(), outputDiv.find("pre"));
});

function getElementPath(element) {
  return "//" + $(element).parents().andSelf().map(function() {
    var $this = $(this);
    var tagName = this.nodeName;
    if ($this.siblings(tagName).length > 0) {
      tagName += "[" + $this.prevAll(tagName).length + "]";
    }
    return tagName;
  }).get().join("/").toUpperCase();
}


var textareas = document.getElementsByClassName("editor")
var editors = []


$(window).load(function() {
  for (var i = 0; i < textareas.length; i++) {
    var textarea = textareas[i]
    var editor = CodeMirror.fromTextArea(textarea, {
      theme: "solarized light",
      matchBrackets: true,
      indentUnit: 2,
      tabSize: 2,
      indentWithTabs: false,
      mode: "text/x-scala",
      smartIndent :false
    });
    editors[i] = editor
  }

});