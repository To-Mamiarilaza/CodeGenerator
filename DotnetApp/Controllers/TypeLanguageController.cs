
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;


public class TypeLanguageController : Controller {
    
    private readonly DatabaseContext _context;
    
    public TypeLanguageController(DatabaseContext context) {
        _context = context;
    }
    
    public async Task<IActionResult> Index() {
        return View(await _context.TypeLanguages.ToListAsync());
    }
    
    public async Task<IActionResult> Edit(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        var typeLanguage = await _context.TypeLanguages.FirstOrDefaultAsync(t => t.IdTypeLanguage == id);
        if(typeLanguage == null)
        {
            return NotFound();
        }
        
        return View("TypeLanguageForm", typeLanguage);
    }
    
    public IActionResult Create() {
        return View("TypeLanguageForm");
    }
    
    [HttpPost]
    public async Task<IActionResult> Update(TypeLanguage typeLanguage) {
        try
        {
            _context.Update(typeLanguage);
            await _context.SaveChangesAsync();
        }
        catch(Exception e)
        {
            ViewData["ErrorMessage"] = e.InnerException.Message;
            return View("TypeLanguageForm", typeLanguage);
        }
        return RedirectToAction(nameof(Index));
        ;
    }
    
    public async Task<IActionResult> Delete(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        
        var typeLanguage = await _context.TypeLanguages.FindAsync(id);
        
        if (typeLanguage == null)
        {
            return NotFound();
        }
        
        _context.TypeLanguages.Remove(typeLanguage);
        await _context.SaveChangesAsync();
        
        return RedirectToAction(nameof(Index));
    }
    
}
