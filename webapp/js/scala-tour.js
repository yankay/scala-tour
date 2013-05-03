function loading(outputDiv) {
  // $(".output").html('<pre><span class="loading">'+('waiting')+'</span></pre>');
  outputDiv.html('<pre><span class="loading">' + ('waiting') + '</span></pre>');
}

function runFunc(codeStr, outputDiv) {
  $.ajax("/run?code=" + encodeURIComponent(codeStr), {
    type: "GET",
    dataType: "json",
    success: function(data) {
      if (!data) {
        return;
      }
      if (data.Errors && data.Errors.length > 0) {
        setOutput(outputDiv, null, null, data.Errors);
        return;
      }
      setOutput(outputDiv, data.Events, data.ErrEvents, false);
    },
    error: function() {
      outputDiv.addClass("error").text(
        "Error communicating with remote server.");
    }
  });
}

function setOutput(output, events, errevents, error) {
  output.empty();
  if (events) {

    var msg = ""
    for (var i = 0; i < events.length; i++) {
      msg += events[i] + "\n"
    }
    output.text(msg);

    msg = ""
    for (var i = 0; i < errevents.length; i++) {
      msg += errevents[i] + "\n"
    }

    if (msg != "") {
      var err = $('<span class="err"/>');
      err.text(msg);
      err.appendTo(output);
    }

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
  }
}

var editors = []
var editorsMap = {}

$(".run").click(function() {
  var editorText = $(this).parent().parent().parent().find(".editor");
  var outputDiv = $(this).parent().parent().parent().find(".output");
  loading(outputDiv);
  var editor = editorsMap[getElementPath(editorText)]
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



document.addEventListener("impress:add-active", function(event) {
  //restore
  var editorsRemove=[]

  for(var i = 0; i < editors.length; i++){
    var editor=editors[i]
    editorsRemove.push(editor)    
  }
  editors = []
  editorsMap = {}
  //add
  var textareas = $(".active .editor").get()
  for (var i = 0; i < textareas.length; i++) {
    var textarea = textareas[i]
    var editor = CodeMirror.fromTextArea(textarea, {
      theme: "solarized light",
      matchBrackets: true,
      indentUnit: 2,
      tabSize: 2,
      indentWithTabs: false,
      mode: "text/x-scala",
      smartIndent :false,
      lineNumbers: false
    });
    editors.push(editor)
    editorsMap[getElementPath(textarea)]=editor
  }

  for(var i = 0; i < editorsRemove.length; i++){
    var editor=editorsRemove[i]    
    window.setTimeout(function() {
      editor.save()    
      editor.toTextArea()
    }, 1000 /* but after 2000 ms */);
  }


}, false);


document.getElementById("body").className = document.getElementById("body").className.replace('onload', ' ');
impress().init();



// var textareas = document.getElementsByClassName("editor")


// $(window).load(function() {





// });