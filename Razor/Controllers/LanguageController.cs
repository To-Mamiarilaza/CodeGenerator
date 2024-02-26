
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;


public class LanguageController : Controller {
    
    private readonly DatabaseContext _context;
    
    public LanguageController(DatabaseContext context) {
        _context = context;
    }
    
    public async Task<IActionResult> Index() {
        return View(await _context.Languages.Include(l => l.TypeLanguage).Include(l => l.Pays).ToListAsync());
    }
    
    public async Task<IActionResult> Edit(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        var language = await _context.Languages.Include(l => l.TypeLanguage).Include(l => l.Pays).FirstOrDefaultAsync(l => l.IdLanguage == id);
        if(language == null)
        {
            return NotFound();
        }
        
        ViewData["TypeLanguages"] = new SelectList(_context.Set<TypeLanguage>(), "IdTypeLanguage", "TypeLanguageName");
        ViewData["Payss"] = new SelectList(_context.Set<Pays>(), "IdPays", "PaysName");
        return View("LanguageForm", language);
    }
    
    public IActionResult Create() {
        ViewData["TypeLanguages"] = new SelectList(_context.Set<TypeLanguage>(), "IdTypeLanguage", "TypeLanguageName");
        ViewData["Payss"] = new SelectList(_context.Set<Pays>(), "IdPays", "PaysName");
        return View("LanguageForm");
    }
    
    [HttpPost]
    public async Task<IActionResult> Update(Language language) {
        try
        {
            _context.Entry(language.TypeLanguage).State = EntityState.Unchanged;
            _context.Entry(language.Pays).State = EntityState.Unchanged;
            _context.Update(language);
            await _context.SaveChangesAsync();
        }
        catch(Exception e)
        {
            ViewData["TypeLanguages"] = new SelectList(_context.Set<TypeLanguage>(), "IdTypeLanguage", "TypeLanguageName");
            ViewData["Payss"] = new SelectList(_context.Set<Pays>(), "IdPays", "PaysName");
            ViewData["ErrorMessage"] = e.InnerException.Message;
            return View("LanguageForm", language);
        }
        return RedirectToAction(nameof(Index));
        ;
    }
    
    public async Task<IActionResult> Delete(int? id) {
        if (id == null)
        {
            return NotFound();
        }
        
        var language = await _context.Languages.FindAsync(id);
        
        if (language == null)
        {
            return NotFound();
        }
        
        _context.Languages.Remove(language);
        await _context.SaveChangesAsync();
        
        return RedirectToAction(nameof(Index));
    }
    
}
